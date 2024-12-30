package com.ajudaqui.gestor360_api.kafka.service

import com.ajudaqui.gestor360_api.kafka.entity.BudgetItems
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class ConsumerService {
    private val logger: Logger = LoggerFactory.getLogger(ConsumerService::class.java)

    @KafkaListener(topics = ["budget_03"], groupId = "gestor-consumer")
    fun consumer(@Payload budgetItems: BudgetItems) {
        logger.info("Recebido: $budgetItems")
    }

}