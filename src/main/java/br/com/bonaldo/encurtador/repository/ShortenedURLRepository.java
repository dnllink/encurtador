package br.com.bonaldo.encurtador.repository;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortenedURLRepository extends MongoRepository<ShortenedURL, String> {}
