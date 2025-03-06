package uz.pdp.education.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.education.dto.ErrorDTO;
import uz.pdp.education.dto.ResponseDTO;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO<Void> exception(MethodArgumentNotValidException e) {
        List<ErrorDTO> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String field = fieldError.getField();
                    String defaultMessage = fieldError.getDefaultMessage();
                    String rejectedValue = String.valueOf(fieldError.getRejectedValue());
                    return new ErrorDTO(
                            field,
                            String.format("defaultMessage: '%s', rejectedValue: '%s'", defaultMessage, rejectedValue)
                    );
                }).toList();
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.BAD_REQUEST.value())  // Bad request kodi
                .message("Validation error")
                .errors(errors)
                .success(false)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseDTO<Void> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.NOT_FOUND.value())  // Not found request kodi
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO<Void> handleException(Exception exception) {
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())   // Internal Server Error request kodi
                .message("Something wrong -> " + exception.getMessage())
                .success(false)
                .build();
    }
}
