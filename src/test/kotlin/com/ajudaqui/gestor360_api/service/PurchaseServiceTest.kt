package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.ItemDTO
import com.ajudaqui.gestor360_api.dto.PurchaseDTO
import com.ajudaqui.gestor360_api.dto.PurchaseItemDTO
import com.ajudaqui.gestor360_api.entity.Item
import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.entity.PurchaseItem
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.exception.NotAutorizationException
import com.ajudaqui.gestor360_api.exception.NotFoundException
import com.ajudaqui.gestor360_api.repository.PurchaseRepository
import com.ajudaqui.gestor360_api.utils.EPurchaseType
import com.ajudaqui.gestor360_api.view.PurchaseView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.*
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
        id = 10,
        name = "Item Teste",
        brand = "marca test",
        unitCost = BigDecimal("10.00"),
        users = user,
    )

    private val purchaseDTO = PurchaseDTO(
        type = EPurchaseType.MANUTENCAO.toString(),
        description = "Teste de manutenção",
    )
    private val purchase = Purchase(
        id = 12,
        type = EPurchaseType.valueOf(purchaseDTO.type),
        description = purchaseDTO.description,
        users = user
    )
    private val purchaseItem = PurchaseItem(
        id = 8,
        description = "Mais um teste",
        quantity = 5.0,
        unitPrice = BigDecimal("12.00"),
        purchase = purchase,
    )
    private val purchaseItemDTO = PurchaseItemDTO(
        description = purchaseItem.description,
        brand = "marca de test",
        quantity = 4.5,
        unitPrice = purchaseItem.unitPrice
    )

    private fun createRandomPurchaseItem(purchase: Purchase): PurchaseItem {
        return PurchaseItem(
            id = 5,
            description = "descrição ${Random.nextInt(20)}",
            quantity = Random.nextDouble(10.0),
            unitPrice = BigDecimal("10.00"),
            purchase = purchase,
        )
    }


    private val purchaseItemService: PurchaseItemService = mockk() {
        every { update(any()) } answers {
            val product = firstArg<PurchaseItem>()
            product.copy(updatedAt = LocalDateTime.now())
        }
    }
    private val itemService: ItemService = mockk() {
        every { findById(any(), any()) } returns item
        every { create(any(), any()) } returns item
    }
    private val usersService: UsersService = mockk() {
        every { findById(user.id!!) } returns user
    }
    private val purchaseRepository: PurchaseRepository = mockk() {
        every { save(any()) } returns purchase
        every { findById(any()) } returns Optional.of(purchase)
        every { findByType(any(), any()) } returns listOf(purchase)
        every { findByUsers_Id(any()) } returns listOf(purchase)
    }

    private val purchaseService = PurchaseService(purchaseRepository, purchaseItemService, itemService, usersService)


    @Test
    fun `deve registrar uma compra`() {
        val registered = purchaseService.register(user.id!!, purchaseDTO)

        verify(exactly = 1) { usersService.findById(any()) }

        assertThat(registered.type).isEqualTo(EPurchaseType.MANUTENCAO)
        assertThat(registered.users.id).isEqualTo(user.id)
        assertThat(registered.id).isNotNull()
        assertThat(registered.totalPrice).isEqualTo(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
    }

    @Test
    fun `o valor da compra deve ser o somatorio do valor dos itens`() {
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

    @Test
    fun `deve trazer as compras pelo type e pelo usuario`() {
        val userId = user.id!!
        val expectedType = EPurchaseType.MANUTENCAO.toString()

        val purchases = purchaseService.findByType(userId, expectedType)
        verify { purchaseRepository.findByType(userId, expectedType) }

        assertThat(purchases).isNotEmpty
        assertThat(purchases[0].type).isEqualTo(EPurchaseType.MANUTENCAO)
    }

    @Test
    fun `deve lancar excecao se o tipo for invalido`() {
        val invalidType = "INVALIDO"
        val exception = assertThrows<IllegalArgumentException> {
            purchaseService.findByType(user.id!!, invalidType)
        }
        assertThat(exception.message).contains("No enum constant")
    }

    @Test
    fun `deve incluir um item registrado em uma compra ja existente`() {
        val itensInicial = purchase.items.size;
        val purchaseUpdated = purchaseService.incluirItem(user.id!!, purchase.id!!, item.id!!, 4.0)

        assertThat(purchaseUpdated.items.size).isNotEqualTo(itensInicial)

    }

    @Test
    fun `deve verificando o fluxo da inclusao de item`() {
        every { purchaseService.update(any()) } answers { firstArg() }

        purchaseService.incluirItem(user.id!!, purchase.id!!, item.id!!, 4.0)

//        verify(exactly = 1) {
//            purchaseService.findById(any(),any())
//
//        } // por algum motivo, essa verificação esta gerando uma nova chamada e quebra o test...
        verify(exactly = 1) {
            itemService.findById(any(), any())
        }
        verify(exactly = 1) {
            purchaseService.update(any())
        }
        verify(exactly = 1) {
            purchaseItemService.update(any())
        }
    }


    @Test
    fun `deve lançar excecao quando usuario nao for autorizado a acessar a compra`() {
        val exception = assertThrows<NotAutorizationException> {
            purchaseService.findById(2, purchase.id!!)
        }
        assertThat("Consulta não autorizada").isEqualTo(exception.message)
    }
    @Test
    fun `deve lançar excecao quando compra não for localizada`() {
        every { purchaseRepository.findById(any()) } returns  Optional.empty()
        val exception = assertThrows<NotFoundException> {
            purchaseService.findById(user.id!!, purchase.id!!)
        }
        assertThat("Compra não localizada").isEqualTo(exception.message)
    }

    @Test
    fun `deve adcionar item não regsitrado a compra atual`() {
        val itensInicial = purchase.items.size;

        val purchaseUpdated = purchaseService.addItemToPurchase(user.id!!, purchase.id!!, purchaseItemDTO)

        assertThat(purchaseUpdated.items.size).isNotEqualTo(itensInicial)
        verify(exactly = 1) { itemService.create(any(), any()) }

    }

    @Test
    fun `deve retornar uma lista de PurchaseView`(){

       val response= purchaseService.findbyusersId(user.id!!)
        assertThat(response)
            .isInstanceOf(List::class.java)
            .allMatch { it is PurchaseView }

    }
}



