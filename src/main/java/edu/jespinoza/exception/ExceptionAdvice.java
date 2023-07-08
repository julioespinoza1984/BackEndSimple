package edu.jespinoza.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
    private static final String TECHNICAL_ERROR = "TECHNICAL ERROR: ";
    private static final String APPLICATION_ERROR = "APPLICATION ERROR: ";
    private static final String UNKNOWN_ERROR = "UNKNOWN ERROR: ";

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<CustomMessage> technicalException(TechnicalException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        CustomMessage message = new CustomMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                TECHNICAL_ERROR + ex.getMessage());
        return ResponseEntity.ok(message);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CustomMessage> applicationException(ApplicationException ex) {
        log.error(ex.getMessage());
        CustomMessage message = new CustomMessage(HttpStatus.BAD_REQUEST.value(),
                APPLICATION_ERROR + ex.getMessage());
        return ResponseEntity.ok(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomMessage> exception(Exception ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        CustomMessage message = new CustomMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                UNKNOWN_ERROR + ex.getMessage());
        return ResponseEntity.ok(message);
    }
}