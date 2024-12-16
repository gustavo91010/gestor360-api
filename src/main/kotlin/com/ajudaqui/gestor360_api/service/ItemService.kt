package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ItemDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.exception.MessageException
import com.ajudaqui.gestor360_api.repository.ItemRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
data class ItemService(
    private val itemRepository: ItemRepository
) {

     fun create(itemDTO: ItemDTO): Item {
        itemRepository.findByNameAndBrand(itemDTO.name, itemDTO.brand).ifPresent() {
            throw MessageException("Item já registrado")
        }
        return itemDTO.let {
            save(Item(name = it.name, brand = it.brand, unitCost = it.unitCost))
        }
    }

    private fun save(item: Item): Item = itemRepository.save(item)
    private fun findById(itemId:Long):Item= itemRepository.findById(itemId)
        .getOrElse {throw NoSuchElementException("Item with ID $itemId not found")  }


    fun findByName(name: String): List<Item> = itemRepository.findByName(name)
    fun findByBrand(brand: String): List<Item> = itemRepository.findByBrand(brand)

    fun findByNameAndBrand(name: String, brand: String): Item =
        itemRepository.findByNameAndBrand(name, brand)
        .getOrElse { throw NoSuchElementException("Item with name $name and brand $brand not found") }

    fun findAll(): List<Item> = itemRepository.findAll()

    fun update(itemDTO: ItemDTO, itemId:Long): Item {
        val item=findById(itemId).copy(
            name = itemDTO.name,
            brand = itemDTO.brand,
            unitCost = itemDTO.unitCost
        )
        return save(item)
    }
    fun delete(itemId:Long) {
        val item = itemRepository.findById(itemId)
            .orElseThrow { NoSuchElementException("Item with ID $itemId not found") }

        itemRepository.delete(item)
    }

}
