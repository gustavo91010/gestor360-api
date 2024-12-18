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


    fun register(productDTO: ProductDTO):Product {
        productRepository.findByName(productDTO.name).ifPresent()
        { throw NotFoundException("Produto já registrado") }

        return productDTO.let {
            save(
                Product(
                    name = it.name,  items = itemService.findByIds(it.itemID)
                )
            )
        }


    }
    fun findAll(): List<Product> = productRepository.findAll()
    fun findByName(name: String): Product =
        productRepository.findByName(name).orElseThrow {
            NotFoundException("Produto não encontrado")
        }

    fun findById(productId: Long): Product = productRepository.findById(productId).orElseThrow {
        NotFoundException("Produto não encontrado")
    }

    fun update(productId: Long, productDTO: ProductDTO): Product {
        var product = findById(productId).copy(
            name = productDTO.name,
            items = itemService.findByIds(productDTO.itemID)
        )
        return save(product)
    }

    private fun save(product: Product): Product = productRepository.save(product)
}