package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.CustomLogger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserValidation.class)
    public ResponseEntity<Object> handleValidacionExceptions(UserValidation exception, WebRequest request) {
        loggerException(exception);
        ErrorMessage errorMessage = new ErrorMessage(exception.getErrorCode(), exception.getMessage());
        HttpStatus httpStatus = (exception.getErrorCode() > HttpStatus.BAD_REQUEST.value()) ? HttpStatus.valueOf(exception.getErrorCode()) : HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(exception, errorMessage, new HttpHeaders(), httpStatus, request);
    }

    private void loggerException(Exception exception) {
        logger.error(CustomLogger.formatError(exception));
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

