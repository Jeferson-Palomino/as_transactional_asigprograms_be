package com.example.t07_backend_notification_soa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Manejador de excepciones, aquí podemos seguir agregando otros tipos de excepciones personalizadas que queremos lanzar.
 * @see <a href="https://rubensa.wordpress.com/2021/05/03/guia-completa-a-la-gestion-de-excepciones-en-spring-boot/#:~:text=Spring%20Boot%20nos%20proporciona%20herramientas,%40ExceptionHandler"/> Guía completa a la gestión de excepciones en Spring Boot </a>
 */
@ControllerAdvice
public class ApiHandlerException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseBody
    public ErrorMessage notFoundException(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.NO_CONTENT.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class
    })
    @ResponseBody
    public ErrorMessage badRequestException(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.BAD_REQUEST.value());
    }
}
