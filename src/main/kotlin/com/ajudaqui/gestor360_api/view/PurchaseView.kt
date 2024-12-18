package com.ajudaqui.gestor360_api.view

import com.ajudaqui.gestor360_api.entity.Purchase
import com.ajudaqui.gestor360_api.entity.PurchaseItem
import java.math.BigDecimal
import java.time.LocalDateTime

data class PurchaseView(
    val id: Long?,
    val type: String,
    val description: String,
    val items: List<PurchaseItem>,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    val usersId: Long?,
    val totalPrice: BigDecimal
)

fun Purchase.toPurchaseView(): PurchaseView {
    return PurchaseView(
        id = this.id,
        type = this.type.toString(),
        description = this.description,
        items = this.items.toList(),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        usersId = this.users.id,
        totalPrice = this.totalPrice
    )
}


