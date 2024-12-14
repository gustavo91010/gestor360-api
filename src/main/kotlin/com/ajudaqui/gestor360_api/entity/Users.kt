package com.ajudaqui.gestor360_api.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
data class Users (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     val id:Long?=null,
     val name:String,
    val email:String,
    val password:String,
    val createdAt: LocalDateTime= LocalDateTime.now(),

    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    val roles:MutableList<Roles> = mutableListOf()
)