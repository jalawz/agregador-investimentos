package tech.buildrun.agregadorinvestimentos.controller.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import tech.buildrun.agregadorinvestimentos.exception.ResourceNotFoundException

@RestControllerAdvice
class ControllerHandler {


    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(exception: ResourceNotFoundException): ResponseEntity<StandardError> {
        val error = exception.message?.let { StandardError(it, HttpStatus.NOT_FOUND.value()) }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }
}