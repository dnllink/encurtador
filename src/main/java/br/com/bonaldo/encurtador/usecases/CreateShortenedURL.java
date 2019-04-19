package br.com.bonaldo.encurtador.usecases;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.gateways.ShortenedURLGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateShortenedURL {

    private final ShortenedURLGateway shortenedURLGateway;

    public ShortenedURL execute(final String originalURL) {
        final ShortenedURL createdURL = new ShortenedURL(originalURL);
        return shortenedURLGateway.save(createdURL);
    }
}