package com.ajudaqui.gestor360_api.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class PurchaseDTO(
    @field:NotEmpty(message = "O tipo não pode estar vazio.") val type: String,
    @field:NotEmpty(message = "A descrição não pode estar vazia.") val description: String,
//    @field:NotNull(message = "A lista de itens não pode ser nula.") var itemID: MutableList<Long>
)
