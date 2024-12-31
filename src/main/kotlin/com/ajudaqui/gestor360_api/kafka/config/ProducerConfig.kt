package com.ajudaqui.gestor360_api.kafka.config

import com.ajudaqui.gestor360_api.kafka.entity.Budget
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class ProducerConfig {
    @Bean
    fun itemProducerTemplate(factor: ProducerFactory<String, Budget>): KafkaTemplate<String, Budget> =
        KafkaTemplate(factor)}