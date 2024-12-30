//package com.ajudaqui.gestor360_api.kafka.config
//
//import com.ajudaqui.gestor360_api.kafka.entity.BudgetItems
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.common.serialization.StringDeserializer
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
//import org.springframework.kafka.config.KafkaListenerContainerFactory
//import org.springframework.kafka.core.ConsumerFactory
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
//import org.springframework.kafka.support.serializer.JsonDeserializer
//
//@Configuration
//class KafkaConsumerConfig {
//
//    @Value("\${spring.kafka.bootstrap-servers}")
//    private lateinit var bootstrapServerUrl: String
//
//    @Bean
//    fun consumerConfig(): Map<String, Any> {
//        val props = mutableMapOf<String, Any>()
//        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServerUrl
//        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
//        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
//        return props
//    }
//
//    @Bean
//    fun consumerFactory(): ConsumerFactory<String, BudgetItems> {
//        val errorHandlingDeserializer = ErrorHandlingDeserializer(JsonDeserializer(BudgetItems::class.java))
//        return DefaultKafkaConsumerFactory(consumerConfig(), StringDeserializer(), errorHandlingDeserializer)
//    }
//
//    @Bean
//    fun listenerContainerFactory(consumerFactory: ConsumerFactory<String, BudgetItems>): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, BudgetItems>> {
//        val factory = ConcurrentKafkaListenerContainerFactory<String, BudgetItems>()
//        factory.consumerFactory = consumerFactory
//        return factory
//    }
//}
//
