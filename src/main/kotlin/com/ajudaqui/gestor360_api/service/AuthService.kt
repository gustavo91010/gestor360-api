package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.LoginDTO
import com.ajudaqui.gestor360_api.exception.NotAutorizationException
import com.ajudaqui.gestor360_api.response.ResponseLogin
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usersService: UsersService,

    ) {

    fun login(loginDTO: LoginDTO): ResponseLogin {
        val user = usersService.findByEmail(loginDTO.email)

        if (!user.password.equals(loginDTO.password)) {
            throw NotAutorizationException("Não autorizado, email / senha incorretos")
        }



       return ResponseLogin(
            id = user.id ?: 0,
            name = user.name,
            email = user.email,
            roles = user.roles
        )
    }
}