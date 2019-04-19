package br.com.bonaldo.encurtador.gateways.controllers;

import br.com.bonaldo.encurtador.gateways.ShortenedURLGateway;
import br.com.bonaldo.encurtador.gateways.controllers.resource.AnalyticsSummaryResource;
import br.com.bonaldo.encurtador.gateways.controllers.resource.AnalyticsURLResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/analytics")
public class AnalyticsController {

    private final ShortenedURLGateway shortenedURLGateway;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AnalyticsSummaryResource getAnalytics() {
        return AnalyticsSummaryResource.builder()
                .totalQuantityURLs(shortenedURLGateway.countURLs())
                .totalClicksOnAllURLs(shortenedURLGateway.totalClicks())
                .topTenMostClicked(
                        shortenedURLGateway
                                .findMostClickedURLs()
                                .stream()
                                .map(AnalyticsURLResource::new)
                                .collect(Collectors.toList()))
                .build();
    }
}
