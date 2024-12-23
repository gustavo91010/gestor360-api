package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ProductDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Product
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.exception.MessageException
import com.ajudaqui.gestor360_api.exception.NotFoundException
import com.ajudaqui.gestor360_api.repository.ProductRepository
import com.ajudaqui.gestor360_api.view.ProductView
import com.ajudaqui.gestor360_api.view.toProductView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test

class ProductServiceTest {

    private val user = Users(
        id = 1,
        name = "User Teste",
        email = "user_test@email.com",
        password = "123456"

    )
    private val item = Item(
        id = 1,
        name = "Item Teste",
        brand = "marca test",
        unitCost = BigDecimal("10.00"),
        users = user,
    )
    private val product = Product(
        id = 1,
        name = "Produto Teste",
        items = listOf(item),
        users = user
    )
    private val produtoDTO = ProductDTO(
        name = "Produto DTO",
        itemID = mutableListOf(1L)
    )
    private val productRepository: ProductRepository = mockk {

        every { save(any()) } answers { // acessa o argumento passado no teste
            val product = firstArg<Product>()
            product.copy(id = 7)
        }
    }
    private var itemService: ItemService = mockk {
        every { findByIds(any()) } returns mutableListOf(item)
    }
    private var usersService: UsersService = mockk {
        every { findById(any()) } returns user
    }

    private val produtoSerice = ProductService(productRepository, itemService, usersService)

    @Test
    fun `deve registrar um produto`() {
        every { productRepository.findByName(any(), any()) } returns Optional.empty()
        produtoSerice.register(1, produtoDTO)

        verify(exactly = 1) { productRepository.findByName(any(), any()) }
        verify(exactly = 1) { productRepository.save(any()) }
    }

    @Test
    fun `deve lançar uma exceção de o produto ja estiver registrado`() {
        every { productRepository.findByName(any(), any()) } returns Optional.of(product)

        var exception = assertThrows<MessageException> {
            produtoSerice.register(1, produtoDTO)

        }
        assertThat(exception.message).isEqualTo("Produto já registrado")


        verify(exactly = 1) { productRepository.findByName(any(), any()) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun `deve lancar uma exception se o produto pelo id for encontradp`() {
        every { productRepository.findById(any(), any()) } returns Optional.empty()

        val exception = assertThrows<NotFoundException> {
            produtoSerice.findById(1, 10)
        }
        assertThat(exception)
            .isInstanceOf(NotFoundException::class.java)
            .hasMessage("Produto não de  id 10 encontrado")

        verify(exactly = 1) { productRepository.findById(1, 1) }
    }
}