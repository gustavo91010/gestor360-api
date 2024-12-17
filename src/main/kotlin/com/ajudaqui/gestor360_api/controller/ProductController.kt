package com.ajudaqui.gestor360_api.controller


import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Product
import com.ajudaqui.gestor360_api.service.ProductDTO
import com.ajudaqui.gestor360_api.service.ProductService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(private val productService: ProductService) {
    private val logger = LoggerFactory.getLogger(ProductController::class.java)

    @Transactional
    @PostMapping
    fun register(@RequestBody itemDTO: ProductDTO): ResponseEntity<Product> {
        logger.info("[POST] | /product | name: ${itemDTO.name}")

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.register(itemDTO))
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<Product>> {
        logger.info("[GET] | /product | ")

        return ResponseEntity.ok(productService.findAll())
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): ResponseEntity<Product> {
        logger.info("[GET] | /product/name/{name} | name: $name")

        return ResponseEntity.ok(productService.findByName(name))
    }


    @GetMapping("/id/{productId}")
    fun findById(@PathVariable productId: Long): ResponseEntity<Product> {
        logger.info("[GET] | /product/id/{productId} | productId: $productId")

        return ResponseEntity.ok(productService.findById(productId))
    }
    @Transactional
    @PutMapping("/{productId}")
    fun update(@RequestBody usersDTO: ProductDTO,@PathVariable productId: Long): ResponseEntity<Product> {
        logger.info("[PUT] | /product/{productId} | itemId: $productId ")

        return ResponseEntity.ok(productService.update(productId, usersDTO))
    }

}
