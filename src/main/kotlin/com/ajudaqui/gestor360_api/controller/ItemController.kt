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
@RequestMapping("/v1/item")
class ItemController(private val itemService: ItemService) {
    private val logger = LoggerFactory.getLogger(ItemController::class.java)

    @Transactional
    @PostMapping
    fun register(@RequestBody itemDTO: ItemDTO,
                 @RequestHeader("Authorization") authHeaderUserId: Long): ResponseEntity<Item> {
        logger.info("[POST] | /item | name: ${itemDTO.name} , userId: $authHeaderUserId")

        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(itemDTO,authHeaderUserId))
    }
    @GetMapping
    fun findAll( @RequestHeader("Authorization") authHeaderUserId: Long): ResponseEntity<List<Item>> {
        logger.info("[GET] | /item | userId: $authHeaderUserId ")

        return ResponseEntity.ok(itemService.findAll(authHeaderUserId))
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String,
                   @RequestHeader("Authorization") authHeaderUserId: Long): ResponseEntity<List<Item>> {
        logger.info("[GET] | /item/name/{name} | name: $name")

        return ResponseEntity.ok(itemService.findByName(authHeaderUserId,name))
    }


    @GetMapping("/brand/{brand}")
    fun findByBrand(@PathVariable brand: String,
                    @RequestHeader("Authorization") authHeaderUserId: Long): ResponseEntity<List<Item>> {
        logger.info("[GET] | /item/brand/{brand} | brand: $brand")

        return ResponseEntity.ok(itemService.findByBrand(authHeaderUserId,brand))
    }

    @GetMapping("/id/{itemId}")
    fun findById(@PathVariable itemId: Long,
                 @RequestHeader("Authorization") authHeaderUserId: Long): ResponseEntity<Item> {
        logger.info("[GET] | /item/id/{itemId} | name: $itemId")

        return ResponseEntity.ok(itemService.findById(authHeaderUserId,itemId))
    }
    @Transactional
    @PutMapping("/{itemId}")
    fun update(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @RequestBody usersDTO: ItemDTO,
        @PathVariable itemId: Long): ResponseEntity<Item> {
        logger.info("[PUT] | /item/{itemId} | itemId: $itemId ")

        return ResponseEntity.ok(itemService.update(usersDTO, itemId,authHeaderUserId))
    }

    @Transactional
    @DeleteMapping("/{itemId}")
    fun delete(
        @RequestHeader("Authorization") authHeaderUserId: Long,
        @PathVariable itemId: Long): ResponseEntity<Void> {
        logger.info("[DELETE] | /item/{itemId} | itemId: $itemId")

        itemService.delete(authHeaderUserId,itemId)
        return ResponseEntity.noContent().build()
    }
}
