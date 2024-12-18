package com.ajudaqui.gestor360_api.controller

import com.ajudaqui.gestor360_api.dto.PurchaseDTO
import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.service.PurchaseService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/purchase")
class PurchaseController(private val purchaseService: PurchaseService) {
    private val logger = LoggerFactory.getLogger(PurchaseController::class.java)

    @Transactional
    @PostMapping("/{userId}")
    fun register(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @RequestBody purchaseDTO: PurchaseDTO): ResponseEntity<Purchase> {
        logger.info("[POST] | /purchase | userId: $authHeaderUserId")

        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.register(authHeaderUserId,purchaseDTO))
    }

    @GetMapping
    fun findAll(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        ): ResponseEntity<List<Purchase>> {
        logger.info("[GET] | /purchase | ")

        return ResponseEntity.ok(purchaseService.findByUsers_Id(authHeaderUserId))
    }

    @GetMapping("/type/{type}")
    fun findByName(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable type: String): ResponseEntity<List<Purchase>> {
        logger.info("[GET] | /purchase/type/{type} | type: $type")

        return ResponseEntity.ok(purchaseService.findByType(authHeaderUserId,type))
    }


    @GetMapping("/id/{purchaseId}")
    fun findById(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable purchaseId: Long): ResponseEntity<Purchase> {
        logger.info("[GET] | /purchase/id/{purchaseId} | purchaseId: $purchaseId")

        return ResponseEntity.ok(purchaseService.findById(authHeaderUserId,purchaseId))
    }

    @GetMapping("/userId/{userId}")
    fun findItemByUser(
        @RequestHeader("Authorization") authHeaderUserId: Long): ResponseEntity<List<Purchase>> {
        logger.info("[GET] | /purchase/userId/{userId} | userId: $authHeaderUserId")

        return ResponseEntity.ok(purchaseService.findByUsers_Id(authHeaderUserId))
    }


}
