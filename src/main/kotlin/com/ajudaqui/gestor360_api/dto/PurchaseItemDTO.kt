package com.ajudaqui.gestor360_api.dto

import com.ajudaqui.gestor360_api.entity.Purchase
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class PurchaseItemDTO(
    @field:NotEmpty val description: String,
    @field:NotEmpty val brand: String,
    @field:NotNull val quantity: Double,
    val unitPrice: BigDecimal = BigDecimal.ZERO,
//    val purchase: Purchase? = null,
)