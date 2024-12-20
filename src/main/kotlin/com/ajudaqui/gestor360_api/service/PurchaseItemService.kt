package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.PurchaseItemDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.PurchaseItem
import com.ajudaqui.gestor360_api.exception.NotAutorizationException
import com.ajudaqui.gestor360_api.repository.PurchaseItemRepository
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Service
data class PurchaseItemService(
    private var purchaseItemRepository: PurchaseItemRepository,
    @Lazy private var purchaseService: PurchaseService
    // a anotação @Lazy  adia a inicialização de uma bean definindo a ordem
) {

    fun create(userId: Long, purchaseItemDTO: PurchaseItemDTO): PurchaseItem = save(
        purchaseItemDTO.let {
            save(
                PurchaseItem(
                    description = it.description,
                    quantity = it.quantity,
                    unitPrice = it.unitPrice,
                    purchase = purchaseService.findById(userId, it.purchaseId)
                )
            )
        }
    )

    fun findByIds(listId: List<Long>): List<PurchaseItem> = purchaseItemRepository.findAllById(listId).toList()
    fun findById(userId: Long, purchaseItemId: Long): PurchaseItem =
        purchaseItemRepository.findById(purchaseItemId).takeIf { it.isPresent }
            ?.get()
            ?.takeIf { it.id == userId }
            ?: throw NotAutorizationException("Item da compra não localizado")

    fun findByPurchaseId(userId: Long, purchaseId: Long): List<PurchaseItem> {

        val itensPurchase = purchaseItemRepository.findByPurchaseId(purchaseId);
        val purchase = itensPurchase.firstOrNull()?.purchase

        return if (purchase != null && purchase.users.id == userId) {
            itensPurchase
        } else {
            throw NotAutorizationException("Usuário não autorizado a acessar esta compra")
        }
    }

    private fun save(purchaseItem: PurchaseItem): PurchaseItem = purchaseItemRepository.save(purchaseItem)


}
