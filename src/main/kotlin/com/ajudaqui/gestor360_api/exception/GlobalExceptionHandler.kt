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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {

        // Extrair os erros de validação
        val errors = ex.bindingResult.allErrors.map {
            val fieldError = it as FieldError
            FieldErrorResponse(fieldError.field, fieldError.defaultMessage ?: "Erro desconhecido")
        }
        val errorResponse = ErrorResponse(
            message =  "Erro de validação",
            details = request.getDescription(false),
            errors = errors
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {

        val errorResponse = ErrorResponse(
            message = ex.message ?: "Dados invalidos",
            details = request.getDescription(false)
        )
        infoLogError(ex)

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(MessageException::class)
    fun handleMessageException(
        ex: MessageException,
        request: WebRequest
    ): ResponseEntity<String> {

        infoLogError(ex)
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }


    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        ex: NotFoundException,
        request: WebRequest
    ): ResponseEntity<String> {

        infoLogError(ex)

        return ResponseEntity(ex.message ?: "Não localizada", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(
        ex: NoSuchElementException,
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