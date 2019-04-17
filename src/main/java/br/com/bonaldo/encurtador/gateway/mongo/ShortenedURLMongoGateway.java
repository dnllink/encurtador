package br.com.bonaldo.encurtador.gateway.mongo;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.gateway.ShortenedURLGateway;
import br.com.bonaldo.encurtador.repository.ShortenedURLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShortenedURLMongoGateway implements ShortenedURLGateway {

    private final ShortenedURLRepository repository;

    @Override
    public ShortenedURL save(final ShortenedURL shortenedURL) {
        return repository.save(shortenedURL);
    }

    @Override
    public Optional<ShortenedURL> findByShortCode(final String shortCode) {
        return repository.findOneByShortCode(shortCode);
    }
}
