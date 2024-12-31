package com.ajudaqui.gestor360_api.kafka.service

import com.ajudaqui.gestor360_api.kafka.dto.BudgetDTO
import com.ajudaqui.gestor360_api.kafka.entity.Budget
import com.ajudaqui.gestor360_api.kafka.entity.BudgetItem
import com.ajudaqui.gestor360_api.service.ItemService
import com.ajudaqui.gestor360_api.utils.ETopics
import org.apache.kafka.common.protocol.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.concurrent.CompletableFuture

@Service
class ProducerService(
    private val template: KafkaTemplate<String, Budget>,
    private val itemService: ItemService
) {
    private val logger: Logger = LoggerFactory.getLogger(ProducerService::class.java)

    fun sendBudget(userId: Long, budgetItensDTO: List<BudgetDTO>) {
        val code = "code: $userId";
        val topic = ETopics.budget_04.toString();
        val itens = itemService.findByIds(budgetItensDTO.map { it.itemId })

        val humta = Array<BudgetItem>(itens.size) { index ->
            BudgetItem.newBuilder()
                .setName(itens[index].name)
                .setBrand(itens[index].brand)
                .setQuantity(budgetItensDTO[index].quantity)
                .build()
        }
        val budget = Budget.newBuilder()
            .setItems(humta.toList())
            .build()
        val message = createMessageWithHeaders(code, budget, topic)
        val future: CompletableFuture<SendResult<String, Budget>> = template.send(message)
        future.whenComplete { result, ex ->
            ex?.also { logger.info("Pessoa enviada com sucesso. MessageId: $code") }
                ?: logger.error("Erro no envio. MessageId: $code, erro: ${ex.message}")
        }
    }

    private fun createMessageWithHeaders(messageId: String, pessoaDTO: Budget, topic: String) =
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