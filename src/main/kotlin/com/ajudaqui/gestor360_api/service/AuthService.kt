package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.LoginDTO
import com.ajudaqui.gestor360_api.dto.UsersDTO
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.exception.MessageException
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
  fun lalala():String{
    return "lalala";
  }
  fun generatedToken(userId: Long, token: String?): String {
    val currentTime = LocalDateTime.now()
    if (!token.isNullOrBlank()) {
      val timeExpered = token.split("#")[2]
      print("timeExpered $timeExpered")
      print("currentTime $currentTime")
      if (currentTime.isBefore(LocalDateTime.parse(timeExpered))) {
        return token
      }
    }

    val expiredAt = currentTime.plusMinutes(60)
    return "${UUID.randomUUID()}#$userId#$expiredAt"
  }

  fun extractToken(token: String){

  }
  fun tokenValidation(token: String): Boolean {
    val parts = token.split("#")

    print("data de validação: ${parts[2]} ")
    val user = usersService.findById(parts[1].toLong())

    val userToken = user.token!!.split("#")[0]
    if (userToken != parts[0]) {
      throw MessageException("Token invalido")
    }

    return false
  }
}
