package com.ajudaqui.gestor360_api.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class PurchaseItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val description: String,
    val quantity: Double,
    val unitPrice: BigDecimal = BigDecimal.ZERO,
    var totalPrice: BigDecimal = BigDecimal.ZERO,

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    val purchase: Purchase? = null
)
{

    @PrePersist
    fun calculateTotalPrice(){
        totalPrice=  unitPrice.multiply(BigDecimal(quantity))
    }
}