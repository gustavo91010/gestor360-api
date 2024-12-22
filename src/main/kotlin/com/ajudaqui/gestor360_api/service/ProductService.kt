package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ProductDTO
import com.ajudaqui.gestor360_api.entity.Product
import com.ajudaqui.gestor360_api.exception.NotFoundException
import com.ajudaqui.gestor360_api.repository.ProductRepository
import com.ajudaqui.gestor360_api.view.ProductView
import com.ajudaqui.gestor360_api.view.toProductView
import org.springframework.stereotype.Service

@Service
class ProductService(
    private var productRepository: ProductRepository,
    private var itemService: ItemService,
    private var usersService: UsersService
) {


    fun register(userId: Long, productDTO: ProductDTO): ProductView {

        productDTO.let {
            productRepository.findByName(userId, it.name).ifPresent()
            { throw NotFoundException("Produto já registrado") }
        }
//        println(itemService.findByIds(productDTO.itemID))

        return productDTO.let {
            save(
                Product(
                    name = it.name,
                    users = usersService.findById(userId),
                    items = itemService.findByIds(it.itemID)
                )
            )
        }.toProductView()
    }

    fun findAll(userId: Long): List<Product> = productRepository.findAll()
    fun findByName(userId: Long, name: String): Product =
        productRepository.findByName(userId, name).orElseThrow {
            NotFoundException("Produto não encontrado")
        }

    fun findById(userId: Long, productId: Long): Product = productRepository.findById(userId, productId).orElseThrow {
        NotFoundException("Produto não id $productId encontrado")
    }

    fun update(userId: Long, productId: Long, productDTO: ProductDTO): Product {
        print("user $userId produtp: $productId")
        val product = findById(userId, productId).copy(
            name = productDTO.name,
            items = itemService.findByIds(productDTO.itemID)
        )
        return save(product)
    }

    private fun save(product: Product): Product = productRepository.save(product)
}