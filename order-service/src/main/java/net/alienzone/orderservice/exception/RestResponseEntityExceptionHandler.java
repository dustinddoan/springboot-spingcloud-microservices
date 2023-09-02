package net.alienzone.orderservice.exception;

import net.alienzone.orderservice.external.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException) {
        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(customException.getMessage())
                .errorCode(customException.getErrorCode())
                .build(), HttpStatus.valueOf(customException.getStatus()));
    }
}
