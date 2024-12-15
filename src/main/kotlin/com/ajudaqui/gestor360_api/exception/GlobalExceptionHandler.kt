package com.ajudaqui.gestor360_api.exception

import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)


    @ExceptionHandler(NotAutorizationException::class)
    fun handleNotAutorizationException(
        ex: NotAutorizationException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {

        val errorResponse = ErrorResponse(
            message = ex.message ?: "Solicitação não autorizada",
            details = request.getDescription(false)
        )
        infoLogError(ex)

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
    fun lalal(){}

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        ex: NotFoundException,
        request: WebRequest
    ): ResponseEntity<String> {

        infoLogError(ex)

        return ResponseEntity(ex.message ?: "Não localizada", HttpStatus.NOT_FOUND)
    }


    private fun infoLogError(ex: Exception) {
        val stackTraceElement = ex.stackTrace.firstOrNull()

        val simpleClassName = stackTraceElement?.className?.substringAfterLast(".") ?: "UnknownClass"
        val methodName = stackTraceElement?.methodName ?: "UnknownMethod"
        val lineNumber = stackTraceElement?.lineNumber ?: -1


        val callerInfo = "Exception occurred in $simpleClassName.$methodName() at line $lineNumber"
        val errorMessage = "Error: ${ex.message}. Called by: $callerInfo"

        logger.warn(errorMessage)
    }

}