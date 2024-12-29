package com.ajudaqui.gestor360_api.kafka.item

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class ProducerConfig {
    @Bean
    fun producerTemplate(factor: ProducerFactory<String, ByteArray>): KafkaTemplate<String, ByteArray> =
        KafkaTemplate(factor)
}
