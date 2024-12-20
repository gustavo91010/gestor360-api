package com.ajudaqui.gestor360_api.controller

import com.ajudaqui.gestor360_api.dto.PurchaseItemDTO
import com.ajudaqui.gestor360_api.entity.PurchaseItem
import com.ajudaqui.gestor360_api.service.PurchaseItemService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/purchase-item")
class PurchaseItemController(
    private val purchaseItemService: PurchaseItemService,
) {
    private val logger = LoggerFactory.getLogger(PurchaseItemController::class.java)

    @Transactional
    @PostMapping
    fun create(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable purchaseId:Long,

        @RequestBody @Valid purchaseDTO: PurchaseItemDTO
    ): ResponseEntity<PurchaseItem> {
        logger.info("[POST] | /purchase-item | userId: $authHeaderUserId")

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(purchaseItemService.create(authHeaderUserId,purchaseId, purchaseDTO))
    }


    @GetMapping("/purchase/{purchaseId}")
    fun findByPurchaseId(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable purchaseId: Long
    ): ResponseEntity<List<PurchaseItem>> {
        logger.info("[GET] | /purchase/purchase/{purchaseId} | purchaseId: $purchaseId")

        return ResponseEntity.ok(purchaseItemService.findByPurchaseId(authHeaderUserId, purchaseId))
    }
    @GetMapping("/id/{purchaseItemId}")
    fun findById(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable purchaseItemId: Long
    ): ResponseEntity<PurchaseItem> {
        logger.info("[GET] | /purchase/id/{purchaseItemId} | purchaseItemId: $purchaseItemId")

        return ResponseEntity.ok(purchaseItemService.findById(authHeaderUserId, purchaseItemId))
    }



}
