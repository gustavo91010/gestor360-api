package com.ajudaqui.gestor360_api.controller


import com.ajudaqui.gestor360_api.entity.Product
import com.ajudaqui.gestor360_api.dto.ProductDTO
import com.ajudaqui.gestor360_api.service.ProductService
import com.ajudaqui.gestor360_api.view.ProductView
import com.ajudaqui.gestor360_api.view.toProductView
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/product")
class ProductController(private val productService: ProductService) {
    private val logger = LoggerFactory.getLogger(ProductController::class.java)

    @Transactional
    @PostMapping
    fun register(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @RequestBody @Valid productDTO: ProductDTO): ResponseEntity<ProductView> {
        logger.info("[POST] | /product | name: ${productDTO.name}")

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.register(authHeaderUserId,productDTO))
    }

    @GetMapping
    fun findAll(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        ): ResponseEntity<List<ProductView>> {
        logger.info("[GET] | /product | userId: $authHeaderUserId")

        return ResponseEntity.ok(productService.findAll(authHeaderUserId).toProductView())
    }

    @GetMapping("/name/{name}")
    fun findByName(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable name: String): ResponseEntity<ProductView> {
        logger.info("[GET] | /product/name/{name} | name: $name")

        return ResponseEntity.ok(productService.findByName(authHeaderUserId,name).toProductView())
    }

//incluir produto view
    @GetMapping("/id/{productId}")
    fun findById(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable productId: Long): ResponseEntity<ProductView> {
        logger.info("[GET] | /product/id/{productId} | productId: $productId")

        return ResponseEntity.ok(productService.findById(authHeaderUserId,productId).toProductView())
    }
    @Transactional
    @PutMapping("/{productId}")
    fun update(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @RequestBody @Valid productDTO: ProductDTO, @PathVariable productId: Long): ResponseEntity<ProductView> {
        logger.info("[PUT] | /product/{productId} | itemId: $productId ")

        return ResponseEntity.ok(productService.update(authHeaderUserId,productId, productDTO).toProductView())
    }

}
