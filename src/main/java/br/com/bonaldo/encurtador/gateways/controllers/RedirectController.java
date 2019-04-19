package br.com.bonaldo.encurtador.gateways.controllers;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.domain.exceptions.UrlNotFoundException;
import br.com.bonaldo.encurtador.gateways.ShortenedURLGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class RedirectController {

    private static final String NOT_FOUND_MESSAGE = "endpoint.redirect.not.found";

    private final ShortenedURLGateway shortenedURLGateway;

    @GetMapping("{code}")
    public ResponseEntity redirectToOriginalURL(@PathVariable final String code) throws UrlNotFoundException {

        final ShortenedURL shortenedURL = shortenedURLGateway.findById(code)
                .orElseThrow(() -> new UrlNotFoundException(NOT_FOUND_MESSAGE));

        log.info("Redirecting using short url id: {} to original url: {}", code, shortenedURL.getOriginalURL());

        shortenedURL.countOneClick();
        shortenedURLGateway.save(shortenedURL);

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, shortenedURL.getOriginalURL())
                .build();
    }
}