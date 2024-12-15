package com.ajudaqui.gestor360_api.entity

import com.ajudaqui.gestor360_api.utils.EPurchaseType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Purchase(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val type: EPurchaseType,
    val description: String,
    val totalPrice: BigDecimal= BigDecimal.ZERO,
    val date: LocalDateTime,

    @OneToMany(mappedBy = "purchase")
    val items: MutableList<PurchaseItem> = mutableListOf()




)
