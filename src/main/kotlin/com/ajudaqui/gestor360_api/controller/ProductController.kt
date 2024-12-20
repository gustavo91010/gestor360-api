package com.ajudaqui.gestor360_api.controller


import com.ajudaqui.gestor360_api.entity.Product
import com.ajudaqui.gestor360_api.dto.ProductDTO
import com.ajudaqui.gestor360_api.service.ProductService
import jakarta.transaction.Transactional
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
        @RequestBody itemDTO: ProductDTO): ResponseEntity<Product> {
        logger.info("[POST] | /product | name: ${itemDTO.name}")

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.register(authHeaderUserId,itemDTO))
    }

    @GetMapping
    fun findAll(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        ): ResponseEntity<List<Product>> {
        logger.info("[GET] | /product | userId: $authHeaderUserId")

        return ResponseEntity.ok(productService.findAll(authHeaderUserId))
    }

    @GetMapping("/name/{name}")
    fun findByName(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable name: String): ResponseEntity<Product> {
        logger.info("[GET] | /product/name/{name} | name: $name")

        return ResponseEntity.ok(productService.findByName(authHeaderUserId,name))
    }


    @GetMapping("/id/{productId}")
    fun findById(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable productId: Long): ResponseEntity<Product> {
        logger.info("[GET] | /product/id/{productId} | productId: $productId")

        return ResponseEntity.ok(productService.findById(authHeaderUserId,productId))
    }
    @Transactional
    @PutMapping("/{productId}")
    fun update(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @RequestBody usersDTO: ProductDTO, @PathVariable productId: Long): ResponseEntity<Product> {
        logger.info("[PUT] | /product/{productId} | itemId: $productId ")

        return ResponseEntity.ok(productService.update(authHeaderUserId,productId, usersDTO))
    }

}
