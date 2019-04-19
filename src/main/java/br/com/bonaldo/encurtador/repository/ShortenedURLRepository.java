package br.com.bonaldo.encurtador.repository;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShortenedURLRepository extends MongoRepository<ShortenedURL, String> {
    List<ShortenedURL> findFirst10ByOrderByClickCounterDesc();
}
