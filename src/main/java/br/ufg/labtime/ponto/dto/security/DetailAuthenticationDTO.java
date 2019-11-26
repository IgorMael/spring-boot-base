package br.ufg.labtime.ponto.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailAuthenticationDTO {
    private Long id;
    private String email;
    private Boolean adm;
    private String name;
    private String authentication;

}
