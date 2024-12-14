package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.Roles
import com.ajudaqui.gestor360_api.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RolesRepository: JpaRepository<Roles, Long>{
    fun findByType(type: String): Optional<Roles>


}