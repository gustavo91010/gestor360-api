import com.ajudaqui.gestor360_api.kafka.entity.ItemAvroDTO
import com.ajudaqui.gestor360_api.kafka.service.item.ItemLALALADTO
import com.ajudaqui.gestor360_api.service.ItemService
import com.ajudaqui.gestor360_api.utils.ETopics
import com.sun.org.slf4j.internal.Logger
import com.sun.org.slf4j.internal.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class ItemProducerImp(
    private val itemTemplate: KafkaTemplate<String, ByteArray>, // Alterando para ByteArray
    private val itemService: ItemService
) {
    private val logger: Logger = LoggerFactory.getLogger(ItemProducerImp::class.java)
//    val topic = ETopics.ITEM.toString()

    fun sendToKafka(topic: String, lalala: List<ItemLALALADTO>) {
        // Converte a lista de objetos para ByteArray usando Avro
        val byteArray = itemListToByteArray(lalala)

        // Criando o objeto da mensagem
        val message: Message<ByteArray> = MessageBuilder
            .withPayload(byteArray)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .build()

        // Enviando a mensagem para o Kafka
        itemTemplate.send(message)
        print("Mensagem enviada com sucesso para o tópico: $topic")
    }

    private fun itemListToByteArray(lalala: List<ItemLALALADTO>): ByteArray {
        // Obtém os itens do serviço
        val itens = itemService.findByIds(lalala.map { it.itemId })
        val itemsList = itens.zip(lalala.map { it.quantity }) { item, quantity ->
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
