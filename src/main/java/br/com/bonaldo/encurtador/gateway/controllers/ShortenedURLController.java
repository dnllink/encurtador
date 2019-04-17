package br.com.bonaldo.encurtador.gateway.controllers;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.gateway.ShortenedURLGateway;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api("Shortened URL")
@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class ShortenedURLController {

    private final ShortenedURLGateway shortenedURLGateway;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShortenedURL createShortenedURL(@RequestBody final ShortenedURL shortenedURL) {
        return shortenedURLGateway.save(shortenedURL);
    }
}