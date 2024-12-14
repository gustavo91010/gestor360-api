package com.ajudaqui.gestor360_api.entity

import com.ajudaqui.gestor360_api.utils.ERoles
import jakarta.persistence.*

@Entity
data class Roles(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(value = EnumType.STRING)
    val type: ERoles
)