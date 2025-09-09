package uz.pdp.education.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> exception(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage));
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())  // Bad request kodi
                .message("Validation error")
                .errors(errors)
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Response<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())  // Not found request kodi
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception exception) {
        return Response.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())   // Internal Server Error request kodi
                .message("Something wrong -> " + exception.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(InvalidHeadersException.class)
    public Response<?> handleInvalidHeadersException(InvalidHeadersException invalidHeadersException) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", "Invalid headers");
        return Response.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(invalidHeadersException.getMessage())
                .errors(errors)
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(CustomizedRequestException.class)
    public Response<?> handleCustomizedRequestException(CustomizedRequestException customizedRequestException) {
        return Response.builder()
                .code(customizedRequestException.getCode())
                .message(customizedRequestException.getMessage())
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
