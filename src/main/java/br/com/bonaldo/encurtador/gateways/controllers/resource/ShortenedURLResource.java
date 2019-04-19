package br.com.bonaldo.encurtador.gateways.controllers.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortenedURLResource {
    @Pattern(regexp = "^(?:http(s)?:\\/\\/).*", message = "endpoint.validation.url.invalid")
    @NotBlank(message = "endpoint.validation.url.null")
    private String originalURL;
    private String shortURL;
}
