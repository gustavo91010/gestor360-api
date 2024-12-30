package com.ajudaqui.gestor360_api.kafka.service

import com.ajudaqui.gestor360_api.kafka.entity.BudgetItem
import com.ajudaqui.gestor360_api.kafka.entity.BudgetItems
import com.ajudaqui.gestor360_api.kafka.service.item.BudgetItemDTO
import com.ajudaqui.gestor360_api.service.ItemService
import com.ajudaqui.gestor360_api.utils.ETopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
data class ProducerService(
    private val template: KafkaTemplate<String, BudgetItems>,
    private val itemService: ItemService
) {
    fun sendBudget(userId: Long, budgetItensDTO: List<BudgetItemDTO>) {

        val itens = itemService.findByIds(budgetItensDTO.map { it.itemId })

        val humta = Array<BudgetItem>(itens.size) { index ->
            BudgetItem.newBuilder()
                .setName(itens[index].name)
                .setBrand(itens[index].brand)
                .setQuantity(budgetItensDTO[index].quantity)
                .build()
        }
        humta.forEach { it.toString() }
        val budgetItems = BudgetItems.newBuilder()
            .setItems(humta.toList())
            .build()

        template.send(ETopics.budget_03.toString(), budgetItems)
    }
}
