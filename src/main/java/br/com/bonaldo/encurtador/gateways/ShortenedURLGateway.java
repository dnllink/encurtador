package br.com.bonaldo.encurtador.gateways;

import br.com.bonaldo.encurtador.domain.ShortenedURL;

import java.util.List;
import java.util.Optional;

public interface ShortenedURLGateway {
    ShortenedURL save(ShortenedURL shortenedURL);
    Optional<ShortenedURL> findById(String code);
    Long countURLs();
    Long totalClicks();
    List<ShortenedURL> findMostClickedURLs();
}
