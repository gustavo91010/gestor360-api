package com.ajudaqui.gestor360_api.view

import com.ajudaqui.gestor360_api.entity.*
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductView(
    val id: Long?,
    val users: Long?,
    val name: String,
    val currentCost: BigDecimal,
    val items: List<Item> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
)

fun Product.toProductView(): ProductView = ProductView(
    id = this.id,
    users = this.users.id,
    name = this.name,
    currentCost = this.currentCost,
    items = this.items,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun List<Product>.toProductView():List<ProductView> = this.map { it.toProductView() }




