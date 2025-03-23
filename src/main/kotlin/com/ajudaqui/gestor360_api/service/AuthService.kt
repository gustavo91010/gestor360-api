package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.LoginDTO
import com.ajudaqui.gestor360_api.dto.UsersDTO
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.exception.NotAutorizationException
import com.ajudaqui.gestor360_api.response.ResponseLogin
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val usersService: UsersService,
) {

  fun login(loginDTO: LoginDTO): ResponseLogin {
    val user = usersService.findByEmail(loginDTO.email)

    return if (user.password == loginDTO.password) {
      // user.token = generatedToken(user.id ?: 0)
      val token = generatedToken(user.id ?: 0, user.token)
      user.token = token
      usersService.save(user)
      ResponseLogin(
              id = user.id ?: 0,
              name = user.name,
              email = user.email,
              token = token,
              roles = user.roles
      )
    } else {
      throw NotAutorizationException("Não autorizado, email/senha incorretos")
    }
  }

  fun register(usersDTO: UsersDTO): Users {
    return if (usersService.emailRegistry(usersDTO.email)) {
      throw NotAutorizationException("Email já registrado")
    } else {
      usersService.create(usersDTO)
    }
  }

  fun generatedToken(userId: Long, token: String?): String {
    val currentTime = LocalDateTime.now()
    //
    // se não tver 5 - sera tratado com null...
    val validToken = token?.takeIf { it.split("-").size == 5 }
    if (!validToken.isNullOrBlank()) {

      val expiredAt = token.split("-").drop(2).joinToString("-")
      val expirationTime = LocalDateTime.parse(expiredAt)

      if (currentTime.isBefore(expirationTime)) {
        return token
      }
    }
    val expiredAt = currentTime.plusMinutes(60)
    var uuid = UUID.randomUUID().toString().replace("-", "")
    return "$uuid-$userId-$expiredAt"
  }

  fun extractIdByToken(token: String): Long {

    val parts = token.split("-")
    return parts[1].toLong()
  }

  fun tokenValidation(token: String, userToken: String): Boolean {
    val parts = token.split("-")
    val currentTime = LocalDateTime.now()
    val expiredAt = token.split("-").drop(2).joinToString("-")
    val expirationTime = LocalDateTime.parse(expiredAt)

    if (currentTime.isAfter(expirationTime)) {
      return false
    }

    val uuidToken = userToken.split("-")[0]
    if (uuidToken != parts[0]) {
      return false
    }
    return true
  }
}
