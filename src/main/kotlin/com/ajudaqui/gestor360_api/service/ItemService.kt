package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ItemDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.exception.MessageException
import com.ajudaqui.gestor360_api.repository.ItemRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
data class ItemService(
    private val itemRepository: ItemRepository,
    private val usersService: UsersService
) {

    fun create(itemDTO: ItemDTO, userId: Long): Item {
        itemDTO.let {
            if (itemRepository.findByNameAndBrand(it.name, it.brand, userId).isNotEmpty()) {
                throw MessageException("Item já registrado")
            }
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
    fun findById(itemId: Long, itemId1: Long): Item = itemRepository.findById(itemId)
        .getOrElse { throw NoSuchElementException("Item with ID $itemId not found") }


    fun findByName(userId: Long, name: String): List<Item> = itemRepository.findByName(userId,name)
    fun findByBrand(userId: Long, brand: String): List<Item> = itemRepository.findByBrand(brand,userId)
    fun findItemByUser(userId: Long): List<Item> = itemRepository.findItemByUser(userId);
    /*
        fun findByNameAndBrand(name: String, brand: String): Item =
        itemRepository.findByNameAndBrand(name, brand, it.userId)
            .getOrElse { throw NoSuchElementException("Item with name $name and brand $brand not found") }
     */


    fun findByIds(listItemId: List<Long>): List<Item> = itemRepository.findAllById(listItemId).toList()


    fun findAll(userId: Long): List<Item> = itemRepository.findItemByUser(userId)

    fun update(itemDTO: ItemDTO, itemId: Long, authHeaderUserId: Long): Item {
        val item = findById(itemId, itemId).copy(
            name = itemDTO.name,
            brand = itemDTO.brand,
            unitCost = itemDTO.unitCost
        )
        return save(item)
    }

    fun delete(itemId: Long, itemId1: Long) {
        val item = itemRepository.findById(itemId)
            .orElseThrow { NoSuchElementException("Item with ID $itemId not found") }

        itemRepository.delete(item)
    }

}
