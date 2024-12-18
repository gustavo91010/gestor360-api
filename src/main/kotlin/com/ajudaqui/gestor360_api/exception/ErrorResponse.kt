package com.ajudaqui.gestor360_api.exception

data class ErrorResponse(
    val message: String,
    val timestamp: String = java.time.LocalDateTime.now().toString(),
    val details: String? = null,
    val errors: List<FieldErrorResponse>? = null
)

data class FieldErrorResponse(
    val field: String,
    val message: String
)