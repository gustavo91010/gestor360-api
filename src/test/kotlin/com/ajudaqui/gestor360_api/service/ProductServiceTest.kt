package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ProductDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Product
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.exception.MessageException
import com.ajudaqui.gestor360_api.exception.NotFoundException
import com.ajudaqui.gestor360_api.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.springframework.web.bind.MethodArgumentNotValidException
import java.math.BigDecimal
import java.math.RoundingMode
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
    private val itens =listOf(item,
        item.copy(id = 2, unitCost = BigDecimal("17.00")),
        item.copy(id = 3, unitCost = BigDecimal("8.00")))
    private var product = Product(
        id = 1,
        name = "Produto Teste",
        items = listOf(item),
        users = user
    )
    private val produtoDTO = ProductDTO(
        name = "Produto DTO",
        itemID = mutableListOf(1L,2L,3L)
    )
    private val productRepository: ProductRepository = mockk {
        every { findById(any(), any()) } returns Optional.of(product)
        every { findByName(any(), any()) } returns Optional.empty()

        every { save(any()) } answers {
            val product = firstArg<Product>() // acessa o argumento passado no teste
            product.copy(id = 7)
        }
    }
    private var itemService: ItemService = mockk {
        every { findByIds(any()) } returns itens
    }
    private var usersService: UsersService = mockk {
        every { findById(any()) } returns user
    }

    private val produtoSerice = ProductService(productRepository, itemService, usersService)

    @Test
    fun `deve adicionar o total dos valores dos itens ao produto`() {
        val registered = produtoSerice.register(1, produtoDTO)
        val totalCost = registered.items.sumOf { it.unitCost }.setScale(2, RoundingMode.HALF_UP)

        assertThat(totalCost).isEqualTo(registered.currentCost)
        assertThat(totalCost).isEqualTo(BigDecimal("35.00")) // Esperado 35.00, itens: 10.00 + 17.00 +8.00
        assertThat(totalCost).isNotZero()
    }

    @Test
    fun `deve registrar um produto`() {
        val userId: Long = 1
        val registered = produtoSerice.register(userId, produtoDTO)

        verify(exactly = 1) { productRepository.findByName(any(), any()) }
        verify(exactly = 1) { productRepository.save(any()) }

        assertThat(registered).isNotNull
        assertThat(registered.usersId).isEqualTo(userId)
        assertThat(registered.name).isEqualTo(produtoDTO.name)
        assertThat(registered.items.map { it.id }).containsExactlyElementsOf(produtoDTO.itemID)
        assertThat(registered.currentCost)
    }

    @Test
    fun `deve lançar uma exceção de o produto ja estiver registrado`() {
        every { productRepository.findByName(any(), any()) } returns Optional.of(product)

        val exception = assertThrows<MessageException> {
            produtoSerice.register(1, produtoDTO)

        }
        assertThat(exception.message).isEqualTo("Produto já registrado")

        verify(exactly = 1) { productRepository.findByName(any(), any()) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun `deve lancar uma exception se o produto pelo id for encontrado`() {
        every { productRepository.findById(any(), any()) } returns Optional.empty()

        val exception = assertThrows<NotFoundException> {
            produtoSerice.findById(1, 10)
        }
        assertThat(exception)
            .isInstanceOf(NotFoundException::class.java)
            .hasMessage("Produto não de  id 10 encontrado")
    }

    @Test
    fun `deve fazer o update dos valores adicionados`() {
        produtoSerice.update(1, 1, produtoDTO)

        verify(exactly = 1) { itemService.findByIds(produtoDTO.itemID) }
        verify(exactly = 1) { productRepository.save(any()) }
    }
}