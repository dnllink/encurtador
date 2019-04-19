package br.com.bonaldo.encurtador.domain.exceptions;

public class UrlNotFoundException extends Exception {
    public UrlNotFoundException(final String message) {
        super(message);
    }
}