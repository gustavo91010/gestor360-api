package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ProductDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Product
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.repository.ProductRepository
import com.ajudaqui.gestor360_api.view.ProductView
import com.ajudaqui.gestor360_api.view.toProductView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test

class ProductServiceTest {

    val user = Users(
        id = 1,
        name = "User Teste",
        email = "user_test@email.com",
        password = "123456"

    )
    val item = Item(
        id = 1,
        name = "Item Teste",
        brand = "marca test",
        unitCost = BigDecimal("10.00"),
        users = user,
    )
    val product = Product(
        id = 1,
        name = "Produto Teste",
        items = listOf(item),
        users = user
    )
    val produtoDTO = ProductDTO(
        name = "Produto DTO",
        itemID = mutableListOf(1L)
    )
    var productRepository: ProductRepository = mockk {
        val saved=product.copy(
            id = 7
        )
        every { save(any()) } returns saved
    }
    var itemService: ItemService = mockk{
        every { findByIds(any()) } returns mutableListOf(item)
    }
    var usersService: UsersService = mockk {
        every { findById(any()) } returns user
    }

    val produtoSerice = ProductService(productRepository, itemService, usersService)


//    / testar o register

    @Test
    fun `deve registrar um produto`() {
        every { productRepository.findByName(any(), any()) } returns Optional.empty()

        produtoSerice.register(1, produtoDTO)

        verify(exactly = 1) { productRepository.findByName(any(), any()) }
        verify(exactly = 1) { productRepository.save(any()) }
    }

    @Test
    fun `deve trazer o produto pelo id`() {
//        every {productRepository. save(any()) } returns product.toProductView()

        produtoSerice.findById(1, 1)


    }
}