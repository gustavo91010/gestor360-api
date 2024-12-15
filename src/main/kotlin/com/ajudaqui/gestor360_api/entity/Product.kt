package com.ajudaqui.gestor360_api.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val currentCost: BigDecimal = BigDecimal.ZERO,

    @ManyToMany
    @JoinTable(
        name = "product_items",
        joinColumns = [JoinColumn(name = "product_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items:  List<Item> = mutableListOf()
    )
