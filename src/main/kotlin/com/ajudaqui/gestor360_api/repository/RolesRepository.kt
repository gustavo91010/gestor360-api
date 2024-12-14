package com.ajudaqui.gestor360_api.repository

import com.ajudaqui.gestor360_api.entity.Roles
import com.ajudaqui.gestor360_api.utils.ERoles
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RolesRepository: JpaRepository<Roles, Long>{
    fun findByType(type: ERoles): Optional<Roles>

}