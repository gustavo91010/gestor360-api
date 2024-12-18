package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.PurchaseDTO
import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.exception.NotFoundException
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
    fun findByUsers_Id(userId:Long):List<Purchase> = purchaseRepository.findByUsers_Id(userId);

    fun findAll(): List<Purchase> = purchaseRepository.findAll()
    fun findByType(type: String): List<Purchase> {
        EPurchaseType.valueOf(type)
        return purchaseRepository.findByType(type)
    }

    fun findById(purchaseId: Long): Purchase = purchaseRepository.findById(purchaseId).orElseThrow {
        NotFoundException("Compra não localizada")
    }

    private fun save(purchase: Purchase): Purchase = purchaseRepository.save(purchase)


}
