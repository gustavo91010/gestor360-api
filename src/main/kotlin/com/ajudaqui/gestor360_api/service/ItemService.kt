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

    private fun create(itemDTO: ItemDTO): Item {

        if (itemRepository.findByNameAndBrand(itemDTO.name,itemDTO.brand).isPresent) {
            throw MessageException("Item já registrado")
        }
        return save(
            Item(name = itemDTO.name, brand = itemDTO.brand, unitCost = itemDTO.unitCost)
        )
    }

    private fun save(item: Item): Item {
        return itemRepository.save(item)
    }

    fun findByName(name: String): List<Item> {
        return itemRepository.findByName(name)
    }
    fun findByBrand(brand: String): List<Item> {
        return itemRepository.findByBrand(brand)
    }
    fun findByNameAndBrand(name: String, brand:String): Item {
        return itemRepository.findByNameAndBrand(name, brand)
            .getOrElse { throw NoSuchElementException("Item de nome '$name' e marca: '${brand} não foi localizado") }
    }
    fun findAll(): List<Item> {
        return itemRepository.findAll()
    }

    fun getItem(name: String): Item {
        // return findByName(name).first{it.name == name}
        return findByName(name).find { it.name == name }
            ?: throw NoSuchElementException("Item de nome '$name' não foi localizado")

    }

    fun update(){}
    fun delete(){}

}
