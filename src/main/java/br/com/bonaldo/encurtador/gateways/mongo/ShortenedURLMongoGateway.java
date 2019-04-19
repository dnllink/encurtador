package br.com.bonaldo.encurtador.gateways.mongo;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.gateways.ShortenedURLGateway;
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
    public Optional<ShortenedURL> findByCode(final String code) {
        return repository.findById(code);
    }
}
