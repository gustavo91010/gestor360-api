package com.ajudaqui.gestor360_api.dto

import java.math.BigDecimal

data class ItemDTO(
    var name:String,
    var brand:String,
    val unitCost: BigDecimal
)
