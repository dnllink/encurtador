package br.com.bonaldo.encurtador.repository;

import br.com.bonaldo.encurtador.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByName(String name);
}
