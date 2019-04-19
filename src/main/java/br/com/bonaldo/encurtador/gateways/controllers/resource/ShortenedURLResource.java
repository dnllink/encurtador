package br.com.bonaldo.encurtador.gateways.controllers.resource;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static org.springframework.util.Assert.notNull;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortenedURLResource {

    private static final String NO_HOST_MESSAGE = "application.error.no.host";

    @Pattern(regexp = "^(?:http(s)?:\\/\\/).*", message = "endpoint.validation.url.invalid")
    @NotBlank(message = "endpoint.validation.url.null")
    private String originalURL;

    private String shortURL;
    private Long clicks;

    public ShortenedURLResource(final ShortenedURL shortenedURL, final String host) {
        notNull(host, NO_HOST_MESSAGE);

        this.originalURL = shortenedURL.getOriginalURL();
        this.shortURL = host.concat(shortenedURL.getId());
        this.clicks = shortenedURL.getClickCounter();
    }
}