package com.ajudaqui.gestor360_api.controller

import com.ajudaqui.gestor360_api.dto.ItemDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.kafka.service.item.budgetItemDTO
import com.ajudaqui.gestor360_api.service.BudgetService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/budget")
class BudgetController(
    private val budgetService: BudgetService
) {
    private val logger = LoggerFactory.getLogger(BudgetController::class.java)

    @Transactional
    @PostMapping
    fun register(@RequestBody budgetItensDTO: List<budgetItemDTO>,
                 @RequestHeader("Authorization") authHeaderUserId: Long): ResponseEntity<String> {
        logger.info("[POST] | /budget | userId: $authHeaderUserId")
        budgetService.sendBudget(authHeaderUserId,budgetItensDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body("Mensagem enviada")
    }
}