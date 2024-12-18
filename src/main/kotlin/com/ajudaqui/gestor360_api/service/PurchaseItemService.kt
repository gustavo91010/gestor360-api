package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.PurchaseItem
import com.ajudaqui.gestor360_api.repository.PurchaseItemRepository
import org.springframework.stereotype.Service

@Service
data class PurchaseItemService(
    private var purchaseRepository: PurchaseItemRepository,
) {

    fun findByIds(listId: List<Long>):List<PurchaseItem> =purchaseRepository.findAllById(listId).toList()

    private fun save(purchaseItem:PurchaseItem):PurchaseItem = purchaseRepository.save(purchaseItem)




}
