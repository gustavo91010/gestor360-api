package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.PurchaseDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.entity.PurchaseItem
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.repository.PurchaseRepository
import com.ajudaqui.gestor360_api.utils.EPurchaseType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import java.math.BigDecimal
import kotlin.random.Random
import kotlin.test.Test

class PurchaseServiceTest {
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
    private val itens = listOf(
        item,
        item.copy(id = 2, unitCost = BigDecimal("17.00")),
        item.copy(id = 3, unitCost = BigDecimal("8.00"))
    )
    private val purchaseDTO = PurchaseDTO(
        type = EPurchaseType.MANUTENCAO.toString(),
        description = "Teste de manutenção",
    )
    private val purchase = Purchase(
        id = Random.nextInt(2).toLong(),
        type = EPurchaseType.valueOf(purchaseDTO.type),
        description = purchaseDTO.description,
        users = user
    )

    private fun createRandomPurchaseItem(purchase: Purchase): PurchaseItem {
        return PurchaseItem(
            id = Random.nextInt(20).toLong(),
            description = "descrição ${Random.nextInt(20)}",
            quantity = Random.nextDouble(10.0),
            unitPrice = BigDecimal("10.00"),
            purchase = purchase,
        )
    }


    private val purchaseItemService: PurchaseItemService = mockk()
    private val itemService: ItemService = mockk()
    private val usersService: UsersService = mockk() {
        every { findById(any()) } returns user
    }
    private val purchaseRepository: PurchaseRepository = mockk() {
        every { save(any()) } returns purchase
    }

    private val purchaseService = PurchaseService(purchaseRepository, purchaseItemService, itemService, usersService)


    @Test
    fun `deve registrar uma compra`() {
        val registered = purchaseService.register(user.id!!, purchaseDTO)

        verify(exactly = 1) { usersService.findById(any()) }

        assertThat(registered.type).isEqualTo(EPurchaseType.MANUTENCAO)
        assertThat(registered.users.id).isEqualTo(user.id)
        assertThat(registered.id).isNotNull()
        assertThat(registered.totalPrice).isEqualTo(BigDecimal.ZERO)
    }

    @Test
    fun `o valor da compra deve ser o somatorio do valro dos itens`() {
        purchase.items.add(createRandomPurchaseItem(purchase))
        purchase.items.add(createRandomPurchaseItem(purchase))
        purchase.items.add(createRandomPurchaseItem(purchase))

        every { purchaseRepository.save(any()) } returns purchase

        val registered = purchaseService.register(user.id!!, purchaseDTO)

        assertThat(registered.totalPrice).isNotZero()
        assertThat(registered.totalPrice).isEqualTo(
            purchase.items.sumOf { it.totalPrice }
        )

    }

}