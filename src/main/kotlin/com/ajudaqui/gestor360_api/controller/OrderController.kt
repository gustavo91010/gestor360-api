package com.ajudaqui.gestor360_api.controller

import com.ajudaqui.gestor360_api.kafka.dto.OrderDTO
import com.ajudaqui.gestor360_api.kafka.service.ProducerService
import com.ajudaqui.gestor360_api.view.MessageView
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/order")
class OrderController(
    private val producerService: ProducerService
) {
    private val logger = LoggerFactory.getLogger(OrderController::class.java)

    @Transactional
    @PostMapping
    fun register(@RequestBody budgetItensDTO: List<OrderDTO>,
                 @RequestHeader("Authorization") authHeaderUserId: Long): ResponseEntity<MessageView> {
        logger.info("[POST] | /order | userId: $authHeaderUserId")
        producerService.send(authHeaderUserId,budgetItensDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageView("Mensagem enviada"))
    }}