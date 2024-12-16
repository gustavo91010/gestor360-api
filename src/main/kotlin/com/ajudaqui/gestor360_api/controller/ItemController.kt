package com.ajudaqui.gestor360_api.controller


import com.ajudaqui.gestor360_api.dto.ItemDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.service.ItemService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/item")
class ItemController(private val itemService: ItemService) {
    private val logger = LoggerFactory.getLogger(ItemController::class.java)

    @Transactional
    @PostMapping
    fun register(@RequestBody itemDTO: ItemDTO): ResponseEntity<Item> {
        logger.info("[POST] | /item | name: ${itemDTO.name}")

        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(itemDTO))
    }
    @GetMapping
    fun findAll(): ResponseEntity<List<Item>> {
        logger.info("[GET] | /item | ")

        return ResponseEntity.ok(itemService.findAll())
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): ResponseEntity<List<Item>> {
        logger.info("[GET] | /item/name/{name} | name: $name")

        return ResponseEntity.ok(itemService.findByName(name))
    }

    @GetMapping("/brand/{brand}")
    fun findByBrand(@PathVariable brand: String): ResponseEntity<List<Item>> {
        logger.info("[GET] | /item/brand/{brand} | brand: $brand")

        return ResponseEntity.ok(itemService.findByBrand(brand))
    }

    @GetMapping("/id/{userId}")
    fun findById(@PathVariable userId: Long): ResponseEntity<Item> {
        logger.info("[GET] | /item/id/{userId} | name: $userId")

        return ResponseEntity.ok(itemService.findById(userId))
    }
    @Transactional
    @PutMapping("/{itemId}")
    fun update(@RequestBody usersDTO: ItemDTO,@PathVariable itemId: Long): ResponseEntity<Item> {
        logger.info("[PUT] | /item/{itemId} | itemId: $itemId ")

        return ResponseEntity.ok(itemService.update(usersDTO, itemId))
    }

    @Transactional
    @DeleteMapping("/{itemId}")
    fun delete(@PathVariable itemId: Long): ResponseEntity<Void> {
        logger.info("[DELETE] | /item/{itemId} | itemId: $itemId")

        itemService.delete(itemId)
        return ResponseEntity.noContent().build()
    }
}
