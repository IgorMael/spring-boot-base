package br.ufg.labtime.ponto.dto.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthenticationDTO {
    private long id;

    private String username;

    private String password;

    private String name;

    private Boolean adm;

    private List<String> authorities;
}

