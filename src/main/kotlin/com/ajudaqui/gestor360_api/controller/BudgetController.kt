package com.ajudaqui.gestor360_api.controller

import com.ajudaqui.gestor360_api.kafka.dto.BudgetDTO
import com.ajudaqui.gestor360_api.kafka.service.ProducerService
import com.ajudaqui.gestor360_api.view.MessageView
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/budget")
class BudgetController(
    private val budgetService: ProducerService
) {
    private val logger = LoggerFactory.getLogger(BudgetController::class.java)

    @Transactional
    @PostMapping
    fun register(@RequestBody budgetItensDTO: List<BudgetDTO>,
                 @RequestHeader("Authorization") authHeaderUserId: Long): ResponseEntity<MessageView> {
        logger.info("[POST] | /budget | userId: $authHeaderUserId")
        budgetService.sendBudget(authHeaderUserId,budgetItensDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageView("Mensagem enviada"))
    }}