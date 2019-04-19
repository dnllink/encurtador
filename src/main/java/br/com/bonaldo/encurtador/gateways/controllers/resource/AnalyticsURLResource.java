package br.com.bonaldo.encurtador.gateways.controllers.resource;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import lombok.Getter;

@Getter
public class AnalyticsURLResource {
    private String url;
    private Long clicks;

    public AnalyticsURLResource(final ShortenedURL shortenedURL) {
        this.url = shortenedURL.getOriginalURL();
        this.clicks = shortenedURL.getClickCounter();
    }
}