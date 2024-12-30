package com.ajudaqui.gestor360_api.kafka.item

import com.ajudaqui.gestor360_api.kafka.entity.BudgetItems
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class ProducerConfig {
    @Bean
    fun itemProducerTemplate(factor: ProducerFactory<String, BudgetItems>): KafkaTemplate<String, BudgetItems> =
        KafkaTemplate(factor)
}
