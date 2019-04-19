package br.com.bonaldo.encurtador.gateways.controllers;

import br.com.bonaldo.encurtador.gateways.ShortenedURLGateway;
import br.com.bonaldo.encurtador.gateways.controllers.resource.AnalyticsSummaryResource;
import br.com.bonaldo.encurtador.gateways.controllers.resource.ShortenedURLResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/analytics")
public class AnalyticsController {

    @Value("${application.domain:http://localhost:8080/}")
    private String hostname;

    private final ShortenedURLGateway shortenedURLGateway;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AnalyticsSummaryResource getAnalytics() {
        log.info("Building analytics data");
        return AnalyticsSummaryResource.builder()
                .totalQuantityURLs(shortenedURLGateway.countURLs())
                .totalClicksOnAllURLs(shortenedURLGateway.totalClicks())
                .topTenMostClicked(
                        shortenedURLGateway
                                .findMostClickedURLs()
                                .stream()
                                .map(shortenedURL -> new ShortenedURLResource(shortenedURL, hostname))
                                .collect(Collectors.toList()))
                .build();
    }
}
