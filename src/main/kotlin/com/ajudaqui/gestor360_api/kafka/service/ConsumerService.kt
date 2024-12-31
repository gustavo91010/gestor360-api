package com.ajudaqui.gestor360_api.kafka.service

import com.ajudaqui.gestor360_api.kafka.entity.Budget
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.PartitionOffset
import org.springframework.kafka.annotation.TopicPartition
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class ConsumerService {
    private val logger: Logger = LoggerFactory.getLogger(ConsumerService::class.java)

    //    @KafkaListener(id = "gestor-consumer", // para ouvir todas
//        topicPartitions = [
//            TopicPartition(
//                topic = "budget_04",
//                partitions = ["0"],// pegando a primeira
//                partitionOffsets = arrayOf(PartitionOffset(partition = "*", initialOffset = "0"))// lendo pratir de todas
//            )
//        ])
    @KafkaListener(topics = ["budget_04"], groupId = "gestor-consumer")// para ouvir a ultima
    fun consumer(@Payload pessoaDTO: Budget) {
        println("aqui nada né...")
        println("aqui nada né...")
        println("aqui nada né...")
//        val pessoa= Pessoa(pessoaDTO.getName().toString(), pessoaDTO.getLastName().toString())
        logger.info("Pessoa recebida: $pessoaDTO")
    }
}