package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.LoginDTO
import com.ajudaqui.gestor360_api.dto.UsersDTO
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.exception.NotAutorizationException
import com.ajudaqui.gestor360_api.response.ResponseLogin
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usersService: UsersService,

    ) {

    fun login(loginDTO: LoginDTO): ResponseLogin {
        val user = usersService.findByEmail(loginDTO.email)

        return if (user.password == loginDTO.password) {
            ResponseLogin(
                id = user.id ?: 0,
                name = user.name,
                email = user.email,
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
}