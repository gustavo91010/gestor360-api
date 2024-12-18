package com.ajudaqui.gestor360_api.dto

data class PurchaseDTO (
    val type:String,
    val description: String,
    var itemID: MutableList<Long>
)
