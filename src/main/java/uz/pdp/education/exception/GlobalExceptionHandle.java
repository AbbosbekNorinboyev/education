package uz.pdp.education.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.education.dto.response.Response;

import java.util.Map;
import java.util.stream.Collectors;

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
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Response<Void> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return Response.<Void>builder()
                .code(HttpStatus.NOT_FOUND.value())  // Not found request kodi
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception exception) {
        return Response.<Void>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())   // Internal Server Error request kodi
                .message("Something wrong -> " + exception.getMessage())
                .success(false)
                .build();
    }
}
