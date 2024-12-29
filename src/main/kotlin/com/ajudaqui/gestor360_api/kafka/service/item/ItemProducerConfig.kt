package com.ajudaqui.gestor360_api.kafka.item

import com.ajudaqui.gestor360_api.kafka.entity.ItemAvroDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class ItemProducerConfig {
    @Bean
    fun itemProducerTemplate(factor: ProducerFactory<String, ItemAvroDTO>): KafkaTemplate<String, ItemAvroDTO> =
        KafkaTemplate(factor)
}
