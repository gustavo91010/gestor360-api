package com.ajudaqui.gestor360_api.dto

import jakarta.validation.constraints.NotEmpty

data class ProductDTO(
    @field:NotEmpty var name: String,
    @field:NotEmpty var itemID: MutableList<Long>)
