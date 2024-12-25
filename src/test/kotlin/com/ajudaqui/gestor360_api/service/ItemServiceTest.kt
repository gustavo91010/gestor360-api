package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ItemDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.exception.MessageException
import com.ajudaqui.gestor360_api.exception.NotAutorizationException
import com.ajudaqui.gestor360_api.exception.NotFoundException
import com.ajudaqui.gestor360_api.repository.ItemRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test

class ItemServiceTest {
    private val user = Users(
        id = 1,
        name = "User Teste",
        email = "user_test@email.com",
        password = "123456"

    )
    private val itemDTO = ItemDTO(
        name = "item test",
        brand = "marca test",
        unitCost = BigDecimal("12.90")
    )

    private val item = itemDTO.let {
        Item(
            id = 1,
            name = it.name,
            brand = it.brand,
            unitCost = it.unitCost,
            users = user
        )
    }

    private val itemRepository: ItemRepository = mockk() {
        every { findByNameAndBrand(any(), any(), any()) } returns emptyList()
        every { findById(any()) } returns Optional.of(item)
        every { save(any()) } returns item
    }
    private val usersService: UsersService = mockk() {
        every { findById(any()) } returns user
    }

    private val itemService = ItemService(itemRepository, usersService)

    @Test
    fun shouldRegisterAnItem() {
        val item = itemService.create(itemDTO, userId = user.id!!)

        assertThat(item.name).isEqualTo(itemDTO.name)
        assertThat(item.users.id).isEqualTo(user.id)

        verify(exactly = 1) { itemRepository.findByNameAndBrand(any(), any(), any()) }
        verify(exactly = 1) { itemRepository.save(any()) }

        assertThat(item.createdAt).isNotNull()
        assertThat(item.updatedAt).isNotNull()
    }

    @Test
    fun shouldThrowExceptionIfItemIsAlreadyRegistered() {
        every { itemRepository.findByNameAndBrand(any(), any(), any()) } returns listOf(item)

        val exception = assertThrows<MessageException> {
            itemService.create(itemDTO, userId = user.id!!)

        }
        assertThat(exception.message).isEqualTo("Item já registrado")
        verify(exactly = 1) { itemRepository.findByNameAndBrand(any(), any(), any()) }
        verify(exactly = 0) { itemRepository.save(any()) }
    }

    @Test
    fun shouldGetItemById(){
     val item=  itemService.findById(user.id!!,item.id!!)
        assertThat(item).isNotNull
        assertThat(item.users.id).isEqualTo(user.id)
    }

    @Test
    fun shouldThrowExceptionIfItenNotFound(){
        every { itemRepository.findById(any()) } returns Optional.empty()
        val exception = assertThrows<NoSuchElementException> {
            itemService.findById(user.id!!, item.id!!)
        }
        assertThat(exception.message).isEqualTo("Item with ID ${item.id!!} not found")
    }
    @Test
    fun shouldThrowExceptionIfUserIdIsInvalid(){
        val exception = assertThrows<NotAutorizationException> {
            itemService.findById(777, item.id!!)
        }
        assertThat(exception.message).isEqualTo("User with ID 777 is not authorized to access item ${item.id!!}")
    }


}