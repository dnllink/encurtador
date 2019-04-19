package br.com.bonaldo.encurtador.gateways.controllers;

import br.com.bonaldo.encurtador.domain.exceptions.UrlNotFoundException;
import br.com.bonaldo.encurtador.gateways.controllers.resource.ErrorResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final MessageSource messageSource;

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResource handleValidationException(final HttpServletRequest request, final MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());
        return new ErrorResource(exception.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> getErrorMessage(objectError.getDefaultMessage()))
                .collect(Collectors.toList()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UrlNotFoundException.class)
    public ErrorResource handleUrlNotFoundException(final HttpServletRequest request, final UrlNotFoundException exception) {
        log.error("Requested short url not found: {}", request.getRequestURI());
        return new ErrorResource(Collections.singletonList(getErrorMessage(exception.getMessage())));
    }

    private String getErrorMessage(final String error) {
        return messageSource.getMessage(error, null, Locale.getDefault());
    }
}