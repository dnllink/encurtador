package br.com.bonaldo.encurtador.gateways.controllers;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.gateways.controllers.resource.ShortenedURLResource;
import br.com.bonaldo.encurtador.usecases.CreateShortenedURL;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("Shortened URL")
@RestController
@RequestMapping("/v1/url")
@RequiredArgsConstructor
public class ShortenedURLController {

    @Value("${application.domain:http://localhost:8080/}")
    private String hostname;

    private final CreateShortenedURL createShortenedURL;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShortenedURLResource createShortenedURL(@RequestBody @Valid final ShortenedURLResource shortenedURL) {
        final ShortenedURL created = saveURL(shortenedURL);
        final String shortURL = getShortURL(created.getId());
        return new ShortenedURLResource(created.getOriginalURL(), shortURL);
    }

    private String getShortURL(final String code) {
        return hostname.concat(code);
    }

    private ShortenedURL saveURL(final ShortenedURLResource shortenedURL) {
        return createShortenedURL.execute(shortenedURL.getOriginalURL());
    }
}