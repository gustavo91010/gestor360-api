package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ProductDTO
import com.ajudaqui.gestor360_api.entity.Product
import com.ajudaqui.gestor360_api.exception.NotFoundException
import com.ajudaqui.gestor360_api.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private var productRepository: ProductRepository,
    private var itemService: ItemService
) {


    fun register(userId: Long, productDTO: ProductDTO): Product {

        productDTO.let {
            productRepository.findByName(userId, it.name).ifPresent()
            { throw NotFoundException("Produto já registrado") }
        }
        return productDTO.let {
            save(
                Product(
                    name = it.name, items = itemService.findByIds(it.itemID)
                )
            )
        }
    }

    fun findAll(userId: Long): List<Product> = productRepository.findAll()
    fun findByName(userId: Long, name: String): Product =
        productRepository.findByName(userId, name).orElseThrow {
            NotFoundException("Produto não encontrado")
        }

    fun findById(userId: Long, productId: Long): Product = productRepository.findById(userId, productId).orElseThrow {
        NotFoundException("Produto não encontrado")
    }

    fun update(userId: Long, productId: Long, productDTO: ProductDTO): Product {
        val product = findById(userId, productId).copy(
            name = productDTO.name,
            items = itemService.findByIds(productDTO.itemID)
        )
        return save(product)
    }

    private fun save(product: Product): Product = productRepository.save(product)
}