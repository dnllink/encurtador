package br.com.bonaldo.encurtador.gateways.controllers.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalyticsSummaryResource {
    private Long totalQuantityURLs;
    private Long totalClicksOnAllURLs;
    private List<ShortenedURLResource> topTenMostClicked;
}
