package com.ajudaqui.gestor360_api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

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
    @JsonIgnore
    val roles: Set<Roles> = emptySet(),
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "users", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    val items: List<Item> = emptyList(),

    @OneToMany(mappedBy = "users")
    @JsonIgnore
    val purchases: List<Purchase> = emptyList(),

    @OneToMany(mappedBy = "users")
    @JsonIgnore
    val products: List<Product> = emptyList()

    ){
    override fun toString(): String {
        return "{id: $id, name: $name, email: $email, createdAt: $createdAt, updatedAt $updatedAt purchases_size: ${purchases.size}, products_size: ${products.size}}"
    }
}


