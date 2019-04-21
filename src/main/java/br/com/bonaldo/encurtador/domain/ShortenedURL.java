package br.com.bonaldo.encurtador.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Document(collection = "urls")
public class ShortenedURL {

    @Id
    private String id;
    private String originalURL;

    @Indexed
    private Long clickCounter;

    public ShortenedURL(final String originalURL) {
        this.originalURL = originalURL;
        this.clickCounter = 0L;
    }

    public void countOneClick() {
        this.clickCounter++;
    }
}