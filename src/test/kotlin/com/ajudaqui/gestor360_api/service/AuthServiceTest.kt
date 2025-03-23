package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.entity.Users
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.assertj.core.api.Assertions.assertThat

class AuthServiceTest {
  private val user =
          Users(id = 1, name = "User Teste", email = "user_test@email.com", password = "123456")

  private var usersService: UsersService = mockk { every { findById(any()) } returns user }
    private val authService = AuthService(usersService)

  @Test
  fun `Token deve ter 5 partes`() {

    // verificação:
    val token = authService.generatedToken(1L, null)
    // assertThat(token.split("-").size).isEqualTo(3)
    assertThat(token.split("-")).hasSize(5)
  }

  @Test
  fun `Deve ter um tempo de 60 min`() {

    val token = authService.generatedToken(1L, "oldToken")
    // pegando apartiro do segundo sinal de -
    val expiredAt = token.split("-").drop(2).joinToString("-")
    val expirationTime = LocalDateTime.parse(expiredAt)

    // Verifica se a expiração é dentro de 60 minutos a partir de agora
    val currentTime = LocalDateTime.now()
    assertThat(expirationTime).isAfterOrEqualTo(currentTime)
  }

  @Test
  fun `deve extrair o id`() {
    // ambiente:
    val token = authService.generatedToken(7L, "oldToken")

    // execiução
    val userID = authService.extractIdByToken(token)
    // Verificação
    assertThat(userID).isEqualTo(7L)
  }

  @Test
  fun `deve validar o tempo de expiração do token`() {
    // ambiente:
    val userToken = "593c974762df434bbc992f5ecb41beb7-7-2025-03-23T01:14:01.354864569"
    val token = "593c974762df434bbc992f5ecb41beb7-7-2025-03-23T01:04:01.354864569"
    // execução:
    var response = authService.tokenValidation(token, userToken)
    assertTrue(response)
  }

  @Test
  fun `deve retornar false o tempo do token tiver expirado`() {
    // ambiente:
    val userToken = "593c974762df434bbc992f5ecb41beb7-7-2025-03-23T00:14:01.354864569"
    val token = "593c974762df434bbc992f5ecb41beb7-7-2025-03-23T00:04:01.354864569"
    // execução:
    var response = authService.tokenValidation(token, userToken)
    assertFalse(response)
  }
  @Test
  fun `deve validar o uuid do token`() {
    val token = authService.generatedToken(7L, "oldToken")
    val userToken = token.replaceFirstChar { 'X' }
    var response = authService.tokenValidation(token, userToken)

    assertFalse(response)
  }
}
