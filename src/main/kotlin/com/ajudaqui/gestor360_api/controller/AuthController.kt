package com.ajudaqui.gestor360_api.controller

import com.ajudaqui.gestor360_api.dto.LoginDTO
import com.ajudaqui.gestor360_api.dto.UsersDTO
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.response.ResponseLogin
import com.ajudaqui.gestor360_api.service.AuthService
import com.ajudaqui.gestor360_api.service.UsersService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService){
     private val logger = LoggerFactory.getLogger(AuthController::class.java)

     @PostMapping
     @Transactional
     fun register(
         @RequestBody usersDTO: UsersDTO
     ): ResponseEntity<Users> {
         logger.info("[POST] | /auth | email: ${usersDTO.email}")
         val userRegistered = authService.register(usersDTO)

         return ResponseEntity.status(201).body(userRegistered)
     }

    @PostMapping("/login")
    @Transactional
    fun login(
        @RequestBody loginDTO: LoginDTO
    ): ResponseEntity<ResponseLogin> {
        logger.info("[POST] | /auth/login | email: ${loginDTO.email}")
        val userRegistered = authService.login(loginDTO)

        return ResponseEntity.status(200).body(userRegistered)
    }
}
