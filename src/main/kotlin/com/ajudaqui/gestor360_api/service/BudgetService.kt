package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.kafka.entity.ItemAvroDTO
import com.ajudaqui.gestor360_api.kafka.service.item.budgetItemDTO
import com.ajudaqui.gestor360_api.utils.ETopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
data class BudgetService(
//    private val sendKakfaService: SendKakfaService,
//    private val itemProducerImp: ItemProducerImp,
    private val template: KafkaTemplate<String, ByteArray>,
    private val itemService: ItemService


){

    fun sendBudget(userId: Long,budgetItensDTO: List<budgetItemDTO>){
        val byteArray = itemListToByteArray(budgetItensDTO)

        sendToKafka(ETopics.BUDGET_01.toString(),byteArray)
    }
    fun sendToKafka(topic: String, byteArray: ByteArray) {

        // Criando o objeto da mensagem
        val message: Message<ByteArray> = MessageBuilder
            .withPayload(byteArray)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .build()

        // Enviando a mensagem para o Kafka
        template.send(message)
        print("Mensagem enviada com sucesso para o tópico: $topic")
    }
    fun itemListToByteArray(budgetItensDTO: List<budgetItemDTO>): ByteArray {
        // Obtém os itens do serviço
        val itens = itemService.findByIds(budgetItensDTO.map { it.itemId })
        val itemsList = itens.zip(budgetItensDTO.map { it.quantity }) { item, quantity ->
            ItemAvroDTO.newBuilder()
                .setName(item.name)
                .setBrand(item.brand)
                .setQuantity(quantity)
                .build()
        }.toList()

        // Serializando a lista de itens para um ByteArray
        val schema = ItemAvroDTO.getClassSchema()
        val writer = org.apache.avro.specific.SpecificData.get().createDatumWriter(schema)
        val outputStream = java.io.ByteArrayOutputStream()
        val encoder = org.apache.avro.io.EncoderFactory.get().binaryEncoder(outputStream, null)

        // Escreve todos os itens no encoder
        itemsList.forEach { item ->
            writer.write(item, encoder)
        }

        encoder.flush()
        return outputStream.toByteArray()
    }
}
