package br.com.bonaldo.encurtador.repository;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShortenedURLRepository extends MongoRepository<ShortenedURL, String> {
    Optional<ShortenedURL> findOneByShortCode(String shortCode);
}
