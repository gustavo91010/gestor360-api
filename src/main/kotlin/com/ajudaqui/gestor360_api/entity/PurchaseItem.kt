package com.ajudaqui.gestor360_api.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.LocalDateTime

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
    val purchase: Purchase? = null,
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    @PrePersist
    fun calculateTotalPrice() {
        totalPrice = unitPrice.multiply(BigDecimal(quantity))
    }


}