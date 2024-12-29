//import com.ajudaqui.gestor360_api.kafka.entity.ItemAvroDTO
//import com.ajudaqui.gestor360_api.kafka.service.item.budgetItemDTO
//import com.ajudaqui.gestor360_api.service.ItemService
//import org.springframework.stereotype.Component
//import org.springframework.stereotype.Service
//
////@Component
////@Service
//class ItemProducerImp(
//    private val itemService: ItemService
//) {
//// Isso poderia ser uma função, e não  uma classe...
//     fun itemListToByteArray(budgetItensDTO: List<budgetItemDTO>): ByteArray {
//        // Obtém os itens do serviço
//        val itens = itemService.findByIds(budgetItensDTO.map { it.itemId })
//        val itemsList = itens.zip(budgetItensDTO.map { it.quantity }) { item, quantity ->
//            ItemAvroDTO.newBuilder()
//                .setName(item.name)
//                .setBrand(item.brand)
//                .setQuantity(quantity)
//                .build()
//        }.toList()
//
//        // Serializando a lista de itens para um ByteArray
//        val schema = ItemAvroDTO.getClassSchema()
//        val writer = org.apache.avro.specific.SpecificData.get().createDatumWriter(schema)
//        val outputStream = java.io.ByteArrayOutputStream()
//        val encoder = org.apache.avro.io.EncoderFactory.get().binaryEncoder(outputStream, null)
//
//        // Escreve todos os itens no encoder
//        itemsList.forEach { item ->
//            writer.write(item, encoder)
//        }
//
//        encoder.flush()
//        return outputStream.toByteArray()
//    }
//}
