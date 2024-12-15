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

        val role = assignRole(ERoles.ROLE_USER)
        val roles: MutableSet<Roles> = HashSet()
        roles.add(role)
        return save(
            Users(
                email = usersDTO.email,
                name = usersDTO.name,
                password = usersDTO.password,
                roles = roles
            )
        )
    }

    private fun save(users: Users): Users {
        return userRepository.save(users)
    }

    fun findById(id: Long): Users {
        return userRepository.findById(id).orElseThrow { NotFoundException("usuário não localizado") }
    }

    fun findByEmail(email: String): Users {
        return userRepository.findByEmail(email).orElseThrow { NotFoundException("usuário não localizado") }

    }
    fun emailIsRegsitered(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

    fun findAll(): MutableList<Users> {
        return userRepository.findAll()

    }

    fun update(usersDTO: UsersDTO, userId: Long): Users {

        var user = findById(userId);
        var userUpdated = user.copy(
            email = usersDTO.email,
            name = usersDTO.name,
            password = usersDTO.password,

            )
        return save(userUpdated)
    }

    fun delete(userId: Long) {
        userRepository.deleteById(userId)


    }

    private fun assignRole(role: ERoles): Roles {
        return rolesRepository.findByType(role).orElseThrow {
            NotFoundException("role não localizada")
        }
    }
}