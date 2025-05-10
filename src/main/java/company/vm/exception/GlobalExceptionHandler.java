package company.vm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(
            {
                    VmGlobalVariableNotFoundException.class,
                    PdfClassNameNotFoundException.class,
                    VmComponentNotFoundException.class,
                    VmComponentVariableLinkNotFoundException.class,
                    VmComponentVariableNotFoundException.class,
                    DateTimeFormatOptionNotFoundException.class,
                    VmTemplateTypeNotFoundException.class,
                    VmTemplateSchemaVersionNotFoundException.class,
                    VmTemplateSchemaTypeNotFoundException.class,
                    VmTemplateNotFoundException.class,
                    VmTemplateSchemaNotFoundException.class,
                    VmTemplateHistoryNotFoundException.class,
                    StringFormatFunctionNotFoundException.class
            }
    )
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Произошла ошибка: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
