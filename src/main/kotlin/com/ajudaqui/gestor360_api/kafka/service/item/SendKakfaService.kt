//package com.ajudaqui.gestor360_api.kafka.service.item
//
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.kafka.support.KafkaHeaders
//import org.springframework.messaging.Message
//import org.springframework.messaging.support.MessageBuilder
//import org.springframework.stereotype.Service
//
////@Service
//class SendKakfaService(
//    private val itemProducerTemplate: KafkaTemplate<String, ByteArray>
//) {
//
//    fun sendToKafka(topic: String, byteArray: ByteArray) {
//
//        // Criando o objeto da mensagem
//        val message: Message<ByteArray> = MessageBuilder
//            .withPayload(byteArray)
//            .setHeader(KafkaHeaders.TOPIC, topic)
//            .build()
//
//        // Enviando a mensagem para o Kafka
//        itemProducerTemplate.send(message)
//        print("Mensagem enviada com sucesso para o tópico: $topic")
//    }
//}