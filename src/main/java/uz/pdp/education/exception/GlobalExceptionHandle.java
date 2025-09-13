package uz.pdp.education.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.education.dto.response.Response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandle {
    // dto va request validatsiya xatolari
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> exception(MethodArgumentNotValidException e,
                                 HttpServletRequest request) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage));
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())  // Bad request kodi
                .message("Validation error")
                .status(HttpStatus.BAD_REQUEST)
                .errors(errors)
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 400 - Parametr validatsiya
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<?> handleConstraintViolation(ConstraintViolationException ex,
                                                 HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(v ->
                errors.put(v.getPropertyPath().toString(), v.getMessage())
        );
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .success(false)
                .errors(errors)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 400 - Noto‘g‘ri argument yuborilgan
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleIllegalArgumentException(IllegalArgumentException ex,
                                                      HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid argument: " + ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 400
    @ExceptionHandler(BadRequestException.class)
    public Response<?> handleBadRequestFoundException(BadRequestException badRequestException,
                                                      HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .message(badRequestException.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public Response<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                       HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())  // Not found request kodi
                .message(resourceNotFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 405 - Noto'g'ri HTTP metod
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .message("Method not allowed: " + ex.getMethod())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 409 - DB constraint buzilgan (masalan duplicate key)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Response<?> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                    HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .message("Conflict: " + ex.getRootCause().getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    // 500
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception exception,
                                       HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())   // Internal Server Error request kodi
                .message("Something wrong -> " + exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(InvalidHeadersException.class)
    public Response<?> handleInvalidHeadersException(InvalidHeadersException invalidHeadersException,
                                                     HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", "Invalid headers");
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(invalidHeadersException.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .errors(errors)
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(CustomizedRequestException.class)
    public Response<?> handleCustomizedRequestException(CustomizedRequestException customizedRequestException,
                                                        HttpServletRequest request) {
        return Response.builder()
                .code(customizedRequestException.getCode())
                .message(customizedRequestException.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(JsonConversionException.class)
    public Response<?> handleJsonConversionException(JsonConversionException ex, HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("JSON conversion error: " + ex.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }
}
