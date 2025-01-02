package com.ajudaqui.gestor360_api.kafka.service

import com.ajudaqui.gestor360_api.kafka.dto.OrderDTO
import com.ajudaqui.gestor360_api.kafka.entity.Order
import com.ajudaqui.gestor360_api.kafka.entity.OrderItem
import com.ajudaqui.gestor360_api.service.ItemService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.CompletableFuture

@Service
class ProducerService(
    private val template: KafkaTemplate<String, Order>,
    private val itemService: ItemService,
) {
    @Value("\${spring.kafka.consumer.topic.order}")
    private lateinit var topic: String

    private val logger: Logger = LoggerFactory.getLogger(ProducerService::class.java)

    fun send(userId: Long, budgetItensDTO: List<OrderDTO>) {
        val code = "code: $userId";

        val itens = itemService.findByIds(budgetItensDTO.map { it.itemId })

        val itensBudget = Array<OrderItem>(itens.size) { index ->
            OrderItem.newBuilder()
                .setName(itens[index].name)
                .setBrand(itens[index].brand)
                .setQuantity(budgetItensDTO[index].quantity)
                .build()
        }
        val order = Order.newBuilder()
            .setOrderId(UUID.randomUUID().toString())
            .setItems(itensBudget.toList())
            .setTimestamp(LocalDateTime.now().toString())
            .build()
        val message = createMessageWithHeaders(code, order, topic)
        val future: CompletableFuture<SendResult<String, Order>> = template.send(message)
        future.whenComplete { result, ex ->
            ex?.also { logger.info("Evento enviada com sucesso. MessageId: $code") }
                ?: logger.error("Erro no envio. MessageId: $code, erro: ${ex.message}")
        }
    }

    private fun createMessageWithHeaders(messageId: String, pessoaDTO: Order, topic: String) =
        MessageBuilder.withPayload(pessoaDTO)
            .setHeader("hash", pessoaDTO.hashCode())
            .setHeader("version", "1.0.0")
            .setHeader("endOfLife", LocalDate.now().plusDays(1L))
            .setHeader("type", "fct")
            .setHeader("cid", messageId)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader(KafkaHeaders.KEY, messageId)
            .build()
}