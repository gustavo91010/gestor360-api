package com.ajudaqui.gestor360_api.controller

import com.ajudaqui.gestor360_api.dto.PurchaseDTO
import com.ajudaqui.gestor360_api.dto.PurchaseItemDTO
import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.service.PurchaseService
import com.ajudaqui.gestor360_api.view.PurchaseView
import com.ajudaqui.gestor360_api.view.toProductView
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/purchase")
class PurchaseController(private val purchaseService: PurchaseService) {
    private val logger = LoggerFactory.getLogger(PurchaseController::class.java)

    @Transactional
    @PostMapping
    fun register(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @RequestBody @Valid purchaseDTO: PurchaseDTO
    ): ResponseEntity<PurchaseView> {
        logger.info("[POST] | /purchase | userId: $authHeaderUserId")

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(purchaseService.register(authHeaderUserId, purchaseDTO).toProductView())
    }

    @Transactional
    @PutMapping("/include")
    fun incluirItem(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @RequestParam purchaseId: Long,
        @RequestParam itemId: Long,
        @RequestParam quantity: Double,
    ): ResponseEntity<PurchaseView> {
        logger.info("[PUT] | /purchase/include | userId: $authHeaderUserId")
        return ResponseEntity.ok(purchaseService.incluirItem(authHeaderUserId, purchaseId,itemId,quantity))
    }
    @Transactional
    @PutMapping("/create-item/{purchaseId}")
    fun addItem(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable purchaseId:Long,
        @RequestBody @Valid purchaseDTO: PurchaseItemDTO
    ): ResponseEntity<PurchaseView> {
        logger.info("[PUT] | /purchase/add | userId: $authHeaderUserId")
        return ResponseEntity.ok(purchaseService.addItemToPurchase(authHeaderUserId,purchaseId, purchaseDTO).toProductView())
    }


    @GetMapping
    fun findAll(
        @RequestHeader("Authorization") authHeaderUserId: Long,
    ): ResponseEntity<List<PurchaseView>> {
        logger.info("[GET] | /purchase | ")

        return ResponseEntity.ok(purchaseService.findbyusersId(authHeaderUserId))
    }

    @GetMapping("/type/{type}")
    fun findByName(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable type: String
    ): ResponseEntity<List<Purchase>> {
        logger.info("[GET] | /purchase/type/{type} | type: $type")

        return ResponseEntity.ok(purchaseService.findByType(authHeaderUserId, type))
    }


    @GetMapping("/id/{purchaseId}")
    fun findById(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable purchaseId: Long
    ): ResponseEntity<PurchaseView> {
        logger.info("[GET] | /purchase/id/{purchaseId} | purchaseId: $purchaseId")

        return ResponseEntity.ok(purchaseService.findById(authHeaderUserId, purchaseId).toProductView())
    }

    @GetMapping("/userId/{userId}")
    fun findItemByUser(
        @RequestHeader("Authorization") authHeaderUserId: Long
    ): ResponseEntity<List<PurchaseView>> {
        logger.info("[GET] | /purchase/userId/{userId} | userId: $authHeaderUserId")

        return ResponseEntity.ok(purchaseService.findbyusersId(authHeaderUserId))
    }


}
