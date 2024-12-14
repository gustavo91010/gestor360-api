package com.ajudaqui.gestor360_api.entity

import jakarta.persistence.*


@Entity
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val email: String,
    val name: String,
    val password: String,

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: Set<Roles> = emptySet()
)

