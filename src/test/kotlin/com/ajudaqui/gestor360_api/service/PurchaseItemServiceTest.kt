package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.entity.PurchaseItem
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.repository.PurchaseItemRepository
import com.ajudaqui.gestor360_api.utils.EPurchaseType
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.Test

class PurchaseItemServiceTest {
    private val user = Users(
        id = 1,
        name = "User Teste",
        email = "user_test@email.com",
        password = "123456"

    )
    private val purchaseItem= PurchaseItem(
        id = 4,
        description = "test",
        quantity = 7.9,
        unitPrice = BigDecimal.TEN,
        createdAt = LocalDateTime.now().minusDays(7),
        purchase = Purchase(
            id = 12,
            type = EPurchaseType.MANUTENCAO,
            description = "lalala",
            users = user
        ),
    )
    private var purchaseItemRepository: PurchaseItemRepository= mockk(){
        every { save(any()) } returns purchaseItem
    }
    private var purchaseService: PurchaseService= mockk()

    private val purchaseItemService= PurchaseItemService(purchaseItemRepository, purchaseService)

    fun `deve criar um purchase item`(){}

    @Test
    fun `deve atualizar o campo updatedAt`(){
        every { purchaseItemRepository.save(any()) } answers {firstArg()}

      val response=  purchaseItemService.update(purchaseItem)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")
        assertThat(purchaseItem.createdAt.format(formatter)).isNotEqualTo(response.updatedAt.format(formatter))

    }
}