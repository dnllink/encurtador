package br.com.bonaldo.encurtador.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "urls")
public class ShortenedURL {

    @Id
    private String id;

    @Indexed
    private String shortCode;

    private String originalURL;
}
