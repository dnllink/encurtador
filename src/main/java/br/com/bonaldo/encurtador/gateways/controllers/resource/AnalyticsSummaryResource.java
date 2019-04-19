package br.com.bonaldo.encurtador.gateways.controllers.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalyticsSummaryResource {
    private Long totalQuantityURLs;
    private Long totalClicksOnAllURLs;
    private List<AnalyticsURLResource> topTenMostClicked;
}
