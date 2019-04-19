package br.com.bonaldo.encurtador.gateways;

import br.com.bonaldo.encurtador.domain.ShortenedURL;

import java.util.Optional;

public interface ShortenedURLGateway {
    ShortenedURL save(ShortenedURL shortenedURL);
    Optional<ShortenedURL> findByCode(String code);
}
