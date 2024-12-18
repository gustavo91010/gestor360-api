package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.PurchaseDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.repository.PurchaseRepository
import com.ajudaqui.gestor360_api.utils.EPurchaseType
import org.springframework.stereotype.Service

@Service
data class PurchaseService(
    private var purchaseRepository: PurchaseRepository,
    private var purchaseItemService: PurchaseItemService,
    private var usersService: UsersService
) {
    fun register(userId: Long, purchaseDTO: PurchaseDTO): Purchase {
        val itens = purchaseItemService.findByIds(purchaseDTO.itemID)

        return purchaseDTO.let {
            save(
                Purchase(
                    users = usersService.findById(userId),
                    items = purchaseItemService.findByIds(purchaseDTO.itemID).toMutableList(),
                    type = EPurchaseType.valueOf(it.type),
                    description = it.description
                )
            )

        }
    }

    private fun save(purchase:Purchase):Purchase = purchaseRepository.save(purchase)

    private fun totalPrice(items: MutableList<Item>) {

    }

}
