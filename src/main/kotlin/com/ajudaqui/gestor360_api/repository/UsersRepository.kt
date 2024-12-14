package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UsersRepository: JpaRepository<Users, Long>{
    fun findByEmail(email: String): Optional<Users>


}