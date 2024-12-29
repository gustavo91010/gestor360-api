package com.ajudaqui.gestor360_api.kafka.service

import com.ajudaqui.gestor360_api.kafka.entity.ItemAvro
import com.ajudaqui.gestor360_api.kafka.entity.ItemAvroDTO
import com.ajudaqui.gestor360_api.kafka.service.item.BudgetItemDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class ConsumerService {
    private val logger: Logger = LoggerFactory.getLogger(ConsumerService::class.java)

//    @KafkaListener(topics = ["budget_01"], groupId = "pessoa-consumer")// para ouvir a ultima
//    fun consumer(@Payload pessoaDTO: List<ItemAvroDTO>) {
//
////        println("schema: ${pessoaDTO.schema}")
//      val um=  pessoaDTO.map { ItemAvro(it.getName().toString(), it.getBrand().toString(), it.getQuantity().toDouble()) }
//        logger.info("Pessoa recebida: ${um}")
//    }
    @KafkaListener(topics = ["budget_01"], groupId = "gestor-consumer")
    fun consumer(@Payload message: ByteArray) {
        val messageAsString = String(message)
        logger.info("Mensagem recebida como String: $messageAsString")
    }

}