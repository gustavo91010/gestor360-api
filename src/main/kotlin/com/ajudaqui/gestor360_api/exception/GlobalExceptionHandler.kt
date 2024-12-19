package com.ajudaqui.gestor360_api.exception

import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)


    @ExceptionHandler(Exception::class)
    fun handleException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val status = exceptionStatus[ex::class.java] ?: HttpStatus.INTERNAL_SERVER_ERROR
        logException(ex)

        val errorResponse = ErrorResponse(
            message = ex.message ?: "Erro desconhecido",
            details = request.getDescription(false)
        )

        return ResponseEntity(errorResponse, status)
    }
    private fun logException(ex: Exception) {
        val stackTraceElement = ex.stackTrace.firstOrNull()

        val simpleClassName = stackTraceElement?.className?.substringAfterLast(".") ?: "UnknownClass"
        val methodName = stackTraceElement?.methodName ?: "UnknownMethod"
        val lineNumber = stackTraceElement?.lineNumber ?: -1

        val callerInfo = "Exception occurred in $simpleClassName.$methodName() at line $lineNumber"
        val errorMessage = "Error: ${ex.message}. Called by: $callerInfo"
        logger.error(errorMessage)
    }
    private  val exceptionStatus = mapOf(
        NoSuchElementException::class.java to HttpStatus.NOT_FOUND,
        MethodArgumentNotValidException::class.java to HttpStatus.BAD_REQUEST,
        IllegalArgumentException::class.java to HttpStatus.BAD_REQUEST,
        MessageException::class.java to HttpStatus.UNAUTHORIZED,
        NotFoundException::class.java to HttpStatus.NOT_FOUND,
        NoSuchElementException::class.java to HttpStatus.NOT_FOUND,
        NotAutorizationException::class.java to HttpStatus.UNAUTHORIZED
    )












}