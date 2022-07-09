package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.CustomLogger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserValidation.class)
    public ResponseEntity<Object> handleValidacionExceptions(UserValidation exception, WebRequest request) {
        loggerException(exception);
        ErrorMessage errorMessage = new ErrorMessage(exception.getErrorCode(), exception.getMessage());
        // HttpStatus httpStatus = (exception.getCodigoError() > HttpStatus.BAD_REQUEST.value()) ? HttpStatus.valueOf(exception.getCodigoError()) : HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(exception, errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    private void loggerException(Exception exception) {
        logger.error(CustomLogger.formatError(exception));
    }
}
