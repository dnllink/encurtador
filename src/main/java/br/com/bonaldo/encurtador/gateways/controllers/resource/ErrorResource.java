package br.com.bonaldo.encurtador.gateways.controllers.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResource {
    private List<String> errors;
}
