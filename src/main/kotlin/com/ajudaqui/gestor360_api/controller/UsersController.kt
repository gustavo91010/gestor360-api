package com.ajudaqui.gestor360_api.controller


import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.dto.UsersDTO
import com.ajudaqui.gestor360_api.service.UsersService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {
    private val logger = LoggerFactory.getLogger(UsersController::class.java)

    @GetMapping
    fun findAll(): ResponseEntity<MutableList<Users>> {
        logger.info("[GET] | /users | ")

        val allUsers = usersService.findAll();
        return ResponseEntity.ok().body(allUsers)
    }

    @GetMapping("/email/{email}")
    fun findByEmail(@PathVariable email: String): ResponseEntity<Users> {
        logger.info("[GET] | /users/email/{email} | email: $email ")

        val user = usersService.findByEmail(email)

        return ResponseEntity.ok().body(user)
    }

    @GetMapping("/id/{userId}")
    fun findById(@PathVariable userId: Long): ResponseEntity<Users> {
        logger.info("[GET] | /users/id/{userId} | userId: $userId ")

        val user = usersService.findById(userId)

        return ResponseEntity.ok().body(user)
    }

    @PutMapping("/{userId}")
    @Transactional
    fun update(
        @RequestBody usersDTO: UsersDTO,
        @PathVariable userId: Long
    ): ResponseEntity<Users> {
        logger.info("[PUT] | /users/{userId} | userId: ${userId} ")

        val user = usersService.update(usersDTO, userId)

        return ResponseEntity.ok().body(user)

    }

    @DeleteMapping("/{userId}")
    @Transactional
    fun delete(@PathVariable userId: Long): ResponseEntity<Void> {
        logger.info("[DELETE] | /users/{userId} | userId: $userId")

        usersService.delete(userId)
        return ResponseEntity.noContent().build()
    }

}
