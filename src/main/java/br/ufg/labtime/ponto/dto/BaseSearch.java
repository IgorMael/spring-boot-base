package br.ufg.labtime.ponto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseSearch {

    private Long id;

    private int page = 0;

    private int quantity = 10;

}
