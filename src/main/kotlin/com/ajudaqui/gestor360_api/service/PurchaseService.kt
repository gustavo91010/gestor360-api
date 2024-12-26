package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ItemDTO
import com.ajudaqui.gestor360_api.dto.PurchaseDTO
import com.ajudaqui.gestor360_api.dto.PurchaseItemDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.entity.PurchaseItem
import com.ajudaqui.gestor360_api.exception.NotAutorizationException
import com.ajudaqui.gestor360_api.exception.NotFoundException
import com.ajudaqui.gestor360_api.repository.PurchaseRepository
import com.ajudaqui.gestor360_api.utils.EPurchaseType
import com.ajudaqui.gestor360_api.utils.toPurchaseItems
import com.ajudaqui.gestor360_api.view.PurchaseView
import com.ajudaqui.gestor360_api.view.toPurchaseView
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
data class PurchaseService(
    private var purchaseRepository: PurchaseRepository,
    private var purchaseItemService: PurchaseItemService,
    private var itemService: ItemService,
    private var usersService: UsersService
) {
    fun register(userId: Long, purchaseDTO: PurchaseDTO): Purchase {
        return purchaseDTO.let {
            save(
                Purchase(
                    users = usersService.findById(userId),
                    type = EPurchaseType.valueOf(it.type),
                    description = it.description,
                    //  items = purchaseItemService.findByIds(purchaseDTO.itemID).toMutableList(),
                )
            )
        }
    }


    fun findbyusersId(userId: Long): List<PurchaseView> =
        purchaseRepository.findByUsers_Id(userId).map { it.toPurchaseView() }

    // fun findAll(authHeaderUserId: Long): List<Purchase> = purchaseRepository.findAll()
    fun findByType(userId: Long, type: String): List<Purchase> {
        EPurchaseType.valueOf(type)
        return purchaseRepository.findByType(userId, type)
    }

    fun findById(userId: Long, purchaseId: Long): Purchase =
        purchaseRepository.findById(purchaseId)
            .orElseThrow { NotFoundException("Compra não localizada") }
            .also {
                if (it.users.id != userId) {
                    throw NotAutorizationException("Consulta não autorizada")
                }
            }


    private fun save(purchase: Purchase): Purchase = purchaseRepository.save(purchase)

    fun incluirItem(userId: Long, purchaseId: Long, itemId: Long, quantity: Double): PurchaseView {
        val purchase = findById(userId, purchaseId)
        val item = itemService.findById(userId, itemId)
        return createPurchaseItem(
            userId = userId,
            purchase = purchase,
            item = item,
            quantity = quantity,
        ).toPurchaseView()
    }

//    fun incluirItens(
//        userId: Long,
//        purchaseId: Long,
//        itensId: List<Long>,
//        quantities: List<Double>
//    ): List<PurchaseItem> =
//        itemService.findByIds(itensId).toPurchaseItems(quantities, findById(userId = userId, purchaseId))


    fun addItemToPurchase(userId: Long, purchaseId: Long, purchaseItemDTO: PurchaseItemDTO): Purchase =
        createPurchaseItem(
            userId = userId,
            purchase = findById(userId = userId, purchaseId),
            item = purchaseItemDTO.let {
                itemService.create(ItemDTO(it.description, it.brand, it.unitPrice), userId)

            },
            quantity = purchaseItemDTO.quantity
        )

    private fun createPurchaseItem(userId: Long, purchase: Purchase, item: Item, quantity: Double): Purchase =
        purchase.takeIf { it.users.id == userId }
            ?.apply { items.add(item.toPurchaseItems(quantity, purchase)) }
            ?.also { purchaseItemService.update(item.toPurchaseItems(quantity, purchase)) }?.also { update(it) }
            ?: throw NotAutorizationException("Usuário não autorizado a acessar esta compra")


    fun update(purchase: Purchase): Purchase = save(purchase.also { it.updatedAt = LocalDateTime.now() })
}
