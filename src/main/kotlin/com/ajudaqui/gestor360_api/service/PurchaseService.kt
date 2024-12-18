package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.PurchaseDTO
import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.exception.NotFoundException
import com.ajudaqui.gestor360_api.repository.PurchaseRepository
import com.ajudaqui.gestor360_api.utils.EPurchaseType
import com.ajudaqui.gestor360_api.view.PurchaseView
import com.ajudaqui.gestor360_api.view.toPurchaseView
import org.springframework.stereotype.Service

@Service
data class PurchaseService(
    private var purchaseRepository: PurchaseRepository,
    private var purchaseItemService: PurchaseItemService,
    private var usersService: UsersService
) {
    fun register(userId: Long, purchaseDTO: PurchaseDTO): Purchase {
        return purchaseDTO.let {
            save(
                Purchase(
                    users = usersService.findById(userId),
                    type = EPurchaseType.valueOf(it.type),
                    description = it.description,
                    items = purchaseItemService.findByIds(purchaseDTO.itemID).toMutableList(),
                )
            )
        }
    }
    fun findbyusersId(userId:Long):List<PurchaseView> =
        purchaseRepository.findByUsers_Id(userId).map { it.toPurchaseView() }

    // fun findAll(authHeaderUserId: Long): List<Purchase> = purchaseRepository.findAll()
    fun findByType(userId: Long, type: String): List<Purchase> {
        EPurchaseType.valueOf(type)
        return purchaseRepository.findByType(userId,type)
    }

    fun findById(purchaseId: Long, purchaseId1: Long): Purchase = purchaseRepository.findById(purchaseId).orElseThrow {
        NotFoundException("Compra não localizada")
    }

    private fun save(purchase: Purchase): Purchase = purchaseRepository.save(purchase)


}
