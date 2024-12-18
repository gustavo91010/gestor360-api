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
                 @PathVariable userId: Long,
                 @RequestBody purchaseDTO: PurchaseDTO): ResponseEntity<Purchase> {
        logger.info("[POST] | /purchase | userId: $userId")

        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.register(userId,purchaseDTO))
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<Purchase>> {
        logger.info("[GET] | /purchase | ")

        return ResponseEntity.ok(/* body = */ purchaseService.findAll())
    }

    @GetMapping("/type/{type}")
    fun findByName(@PathVariable type: String): ResponseEntity<List<Purchase>> {
        logger.info("[GET] | /purchase/type/{type} | type: $type")

        return ResponseEntity.ok(purchaseService.findByType(type))
    }


    @GetMapping("/id/{purchaseId}")
    fun findById(@PathVariable purchaseId: Long): ResponseEntity<Purchase> {
        logger.info("[GET] | /purchase/id/{purchaseId} | purchaseId: $purchaseId")

        return ResponseEntity.ok(purchaseService.findById(purchaseId))
    }

    @GetMapping("/userId/{userId}")
    fun findItemByUser(@PathVariable userId: Long): ResponseEntity<List<Purchase>> {
        logger.info("[GET] | /purchase/userId/{userId} | userId: $userId")

        return ResponseEntity.ok(purchaseService.findByUsers_Id(userId))
    }


}
