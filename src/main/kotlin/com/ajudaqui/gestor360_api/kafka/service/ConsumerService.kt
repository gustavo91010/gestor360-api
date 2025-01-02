package com.ajudaqui.gestor360_api.kafka.service

import com.ajudaqui.gestor360_api.kafka.entity.Order
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class ConsumerService {
    private val logger: Logger = LoggerFactory.getLogger(ConsumerService::class.java)


//    @KafkaListener(topics = ["\${spring.kafka.consumer.topic.order}"], groupId = "gestor-consumer")
    fun consumer(@Payload order: Order) {
        logger.info("Mensagem recebida: $order")
    }
    @KafkaListener(topics = ["\${spring.kafka.consumer.topic.financial}"], groupId = "gestor-consumer")
    fun consumer(@Payload financial: String) {
        logger.info("Mensagem recebida: $financial")
    }
    @KafkaListener(topics = ["\${spring.kafka.consumer.topic.closing}"], groupId = "gestor-consumer")
    fun consumer(@Payload closing: Long) {
        logger.info("Mensagem recebida: $closing")
    }
}