package com.ajudaqui.gestor360_api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

@Entity
data class PurchaseItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val description: String,
    val quantity: Double,
    val unitPrice: BigDecimal = BigDecimal.ZERO,
//    var totalPrice: BigDecimal = BigDecimal.ZERO,

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    @JsonIgnore
    val purchase: Purchase? = null,
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val totalPrice: BigDecimal
        get() = unitPrice.multiply(BigDecimal(quantity)).setScale(2,RoundingMode.HALF_UP)

//    val value = BigDecimal("123.456").setScale(2, RoundingMode.CEILING)
//    @PrePersist
//    fun calculateTotalPrice() {
//        totalPrice = unitPrice.multiply(BigDecimal(quantity))
//    }


}