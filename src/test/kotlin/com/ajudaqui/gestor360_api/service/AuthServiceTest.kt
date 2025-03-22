package com.ajudaqui.gestor360_api.service

import io.mockk.mockk
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.apache.catalina.User

class AuthServiceTest {
  private var usersService: UsersService = mockk {  }
  // private var authService: AuthService = mockk {}
private val authService = AuthService(usersService)

  @Test
  fun `lalala`(){
     var lala= authService.lalala()
     assertThat(lala).isEqualTo("lalala")
  }
  @Test
  fun `Token deve ter 3 partes`() {

    // verificação:
    val token = authService.generatedToken(1L, null)
    // assertThat(token.split("-").size).isEqualTo(3)
    assertThat(token.split("#")).hasSize(3)

  }

  @Test
  fun `Token deve expirar com 60 min`() {

    val token = authService.generatedToken(1L, null)
        val expirationTime = LocalDateTime.parse(token.split("#")[2])
        // Verifica se a expiração é dentro de 60 minutos a partir de agora
    val currentTime = LocalDateTime.now()
    val expectedExpirationTime = currentTime.plusMinutes(60)

    // Verifica se a diferença entre a hora de expiração e a hora atual está dentro do esperado
    assertThat(expirationTime).isAfterOrEqualTo(expectedExpirationTime.minusMinutes(1))
    assertThat(expirationTime).isBeforeOrEqualTo(expectedExpirationTime.plusMinutes(1))

    // println("------------------------------------------------")
    // println("ja " + token.split("-")[2])
    // println(LocalDateTime.parse(token.split("-")[2]))
    // println("------------------------------------------------")
    // assertThat(token.split("-")[2]).isEqualTo("lalala")
  }
}
    // private var usersService: UsersService = mockk {
    //     every { findById(any()) } returns user
    // }
    // @Test
    // fun `deve adicionar o total dos valores dos itens ao produto`() {
    //     val registered = produtoSerice.register(1, produtoDTO)
    //     val totalCost = registered.items.sumOf { it.unitCost }.setScale(2, RoundingMode.HALF_UP)

    //     assertThat(totalCost).isEqualTo(registered.currentCost)
    //     assertThat(totalCost).isEqualTo(BigDecimal("35.00")) // Esperado 35.00, itens: 10.00 +
    // 17.00 +8.00
    //     assertThat(totalCost).isNotZero()
    // }
