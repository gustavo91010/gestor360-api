package com.ajudaqui.gestor360_api.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        val messageException = formatMessageException(ex)

        logger.error(messageException)

        val errorResponse = ErrorResponse(
            message = messageException,
            details = request.getDescription(false)
        )

        return ResponseEntity(errorResponse, status)
    }

    private fun formatMessageException(ex: Exception): String {
        val stackTraceElement = ex.stackTrace
            .firstOrNull { it.className.startsWith("com.ajudaqui.gestor360_api") }

        val callerInfo: String = if (stackTraceElement != null) {

            val simpleClassName = stackTraceElement.className.substringAfterLast(".")
            val methodName = stackTraceElement.methodName
            val lineNumber = stackTraceElement.lineNumber

            "Error of type ${ex::class.java.simpleName}: ${ex.message} in $simpleClassName.$methodName() at line $lineNumber"
        } else {
            "Error: ${ex.message}. No relevant stack trace found."
        }
        return callerInfo
    }


    private val exceptionStatus = mapOf(
        NoSuchElementException::class.java to HttpStatus.NOT_FOUND,
        MethodArgumentNotValidException::class.java to HttpStatus.BAD_REQUEST,
        IllegalArgumentException::class.java to HttpStatus.BAD_REQUEST,
        MessageException::class.java to HttpStatus.UNAUTHORIZED,
        NotFoundException::class.java to HttpStatus.NOT_FOUND,
        NoSuchElementException::class.java to HttpStatus.NOT_FOUND,
        NotAutorizationException::class.java to HttpStatus.UNAUTHORIZED
    )


}