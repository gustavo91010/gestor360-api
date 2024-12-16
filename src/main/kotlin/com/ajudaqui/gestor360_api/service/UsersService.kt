package com.ajudaqui.gestor360_api.service

import com.ajudaqui.gestor360_api.dto.UsersDTO
import com.ajudaqui.gestor360_api.entity.Roles
import com.ajudaqui.gestor360_api.entity.Users
import com.ajudaqui.gestor360_api.exception.NotFoundException
import com.ajudaqui.gestor360_api.repository.RolesRepository
import com.ajudaqui.gestor360_api.repository.UsersRepository
import com.ajudaqui.gestor360_api.utils.ERoles
import org.springframework.stereotype.Service

@Service
class UsersService(
    private val userRepository: UsersRepository,
    private val rolesRepository: RolesRepository
) {

    fun create(usersDTO: UsersDTO): Users {

        val roles: MutableSet<Roles> = mutableSetOf(assignRole(ERoles.ROLE_USER))

        return usersDTO.let {
            save(Users(name = it.name, email = it.email, password = it.password, roles = roles))
        }
    }

    private fun save(users: Users): Users = userRepository.save(users)

    fun findById(id: Long): Users =
        userRepository.findById(id).orElseThrow { NotFoundException("user not found") }

    fun findByEmail(email: String): Users =
        userRepository.findByEmail(email).orElseThrow { NotFoundException("user not found") }

    fun emailRegistry(email: String): Boolean = userRepository.findByEmail(email).isPresent

    fun findAll(): MutableList<Users> = userRepository.findAll()

    fun update(usersDTO: UsersDTO, userId: Long): Users {
        val user = findById(userId).copy(
            email = usersDTO.email,
            name = usersDTO.name,
            password = usersDTO.password
        )
        return save(user)
    }


    fun delete(userId: Long) = userRepository.deleteById(userId)

    private fun assignRole(role: ERoles): Roles =
        rolesRepository.findByType(role).orElseThrow {
        NotFoundException("role not found")
    }
}