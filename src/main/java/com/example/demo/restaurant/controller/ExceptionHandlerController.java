package com.example.demo.restaurant.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.log.LogFormatUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.demo.restaurant.dto.ExceptionMessage;
import com.example.demo.restaurant.exception.ETagDoesntMatchException;
import com.example.demo.restaurant.exception.ResourceNotFoundException;

import java.lang.reflect.InvocationTargetException;

/**
 * Exception handler for Spring Data REST controllers.
 *
 * @author Thibaud Lepretre
 * @author Oliver Gierke
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerController {

    private static ResponseEntity<?> notFound(HttpHeaders headers) {
        return response(HttpStatus.NOT_FOUND, headers, null);
    }

    private static ResponseEntity<ExceptionMessage> badRequest(HttpHeaders headers, Exception throwable) {
        return errorResponse(HttpStatus.BAD_REQUEST, headers, throwable);
    }

    private static ResponseEntity<ExceptionMessage> errorResponse(HttpStatus status, HttpHeaders headers,
                                                                  Exception exception) {

        if (exception != null) {

            String message = exception.getMessage();
            log.debug(LogFormatUtils.formatValue(message, -1, true), exception);

            if (StringUtils.hasText(message)) {
                return response(status, headers, new ExceptionMessage(exception));
            }
        }

        return response(status, headers, null);
    }

    private static <T> ResponseEntity<T> response(HttpStatus status, HttpHeaders headers) {
        return response(status, headers, null);
    }

    private static <T> ResponseEntity<T> response(HttpStatus status, HttpHeaders headers, T body) {

        Assert.notNull(headers, "Headers must not be null");
        Assert.notNull(status, "HttpStatus must not be null");

        return new ResponseEntity<T>(body, headers, status);
    }

    /**
     * Handles {@link ResourceNotFoundException} by returning {@code 404 Not Found}.
     *
     * @param o_O the exception to handle.
     * @return
     */
    @ExceptionHandler
    ResponseEntity<?> handleNotFound(ResourceNotFoundException o_O) {
        return notFound(new HttpHeaders());
    }

    /**
     * Handles {@link HttpMessageNotReadableException} by returning {@code 400 Bad Request}.
     *
     * @param o_O the exception to handle.
     * @return
     */
    @ExceptionHandler
    ResponseEntity<ExceptionMessage> handleNotReadable(HttpMessageNotReadableException o_O) {
        return badRequest(new HttpHeaders(), o_O);
    }

    /**
     * Handle failures commonly thrown from code tries to read incoming data and convert or cast it to the right type by
     * returning {@code 500 Internal Server Error} and the thrown exception marshalled into JSON.
     *
     * @param o_O the exception to handle.
     * @return
     */
    @ExceptionHandler({InvocationTargetException.class, IllegalArgumentException.class, ClassCastException.class,
            ConversionFailedException.class, NullPointerException.class})
    ResponseEntity<ExceptionMessage> handleMiscFailures(Exception o_O) {

        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, new HttpHeaders(), o_O);
    }

    /**
     * Send a {@code 409 Conflict} in case of concurrent modification.
     *
     * @param o_O the exception to handle.
     * @return
     */
    @ExceptionHandler({OptimisticLockingFailureException.class, DataIntegrityViolationException.class})
    ResponseEntity<ExceptionMessage> handleConflict(Exception o_O) {
        return errorResponse(HttpStatus.CONFLICT, new HttpHeaders(), o_O);
    }

    /**
     * Send {@code 405 Method Not Allowed} and include the supported {@link org.springframework.http.HttpMethod}s in the
     * {@code Allow} header.
     *
     * @param o_O the exception to handle.
     * @return
     */
    @ExceptionHandler
    ResponseEntity<Void> handle(HttpRequestMethodNotSupportedException o_O) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAllow(o_O.getSupportedHttpMethods());

        return response(HttpStatus.METHOD_NOT_ALLOWED, headers);
    }

    /**
     * Handles {@link ETagDoesntMatchException} by returning {@code 412 Precondition Failed}.
     *
     * @param o_O the exception to handle.
     * @return
     */
    @ExceptionHandler
    ResponseEntity<Void> handle(ETagDoesntMatchException o_O) {

        HttpHeaders headers = o_O.getExpectedETag().addTo(new HttpHeaders());
        return response(HttpStatus.PRECONDITION_FAILED, headers);
    }

}
