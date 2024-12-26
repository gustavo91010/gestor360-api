package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ItemDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.exception.MessageException
import com.ajudaqui.gestor360_api.exception.NotAutorizationException
import com.ajudaqui.gestor360_api.repository.ItemRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrElse

@Service
data class ItemService(
    private val itemRepository: ItemRepository, private val usersService: UsersService
) {

    fun create(itemDTO: ItemDTO, userId: Long): Item {
        if (itemRepository.findByNameAndBrand(itemDTO.name, itemDTO.brand, userId).isNotEmpty()) {
            throw MessageException("Item já registrado")
        }
        return itemDTO.let {
            save(
                Item(
                    name = it.name,
                    brand = it.brand,
                    unitCost = it.unitCost,
                    users = usersService.findById(userId),
                )
            )
        }
    }


    private fun save(item: Item): Item = itemRepository.save(item)

    fun findById(userId: Long, itemId: Long): Item =
        itemRepository.findById(itemId).also { println("sera que vema qui no item???") }
            .getOrElse { throw NoSuchElementException("Item with ID $itemId not found") }
            .takeIf { it.users.id == userId }
            ?: throw NotAutorizationException("User with ID $userId is not authorized to access item $itemId")


    fun findByName(userId: Long, name: String): List<Item> = itemRepository.findByName(userId, name)
    fun findByBrand(userId: Long, brand: String): List<Item> = itemRepository.findByBrand(brand, userId)

    fun findByIds(listItemId: List<Long>): List<Item> = itemRepository.findAllById(listItemId).toList()


    fun findAll(userId: Long): List<Item> = itemRepository.findItemByUser(userId)

    fun update(itemDTO: ItemDTO, itemId: Long, authHeaderUserId: Long): Item {
        return itemDTO.let {
            save(
                findById(itemId, itemId).copy(
                    name = it.name,
                    brand = it.brand,
                    unitCost = it.unitCost,
                    updatedAt = LocalDateTime.now()
                )
            )
        }
    }

    fun delete(itemId: Long, itemId1: Long) {
        val item =
            itemRepository.findById(itemId).orElseThrow { NoSuchElementException("Item with ID $itemId not found") }

        itemRepository.delete(item)
    }

}
