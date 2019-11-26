package br.ufg.labtime.ponto.dto.user;

import br.ufg.labtime.ponto.dto.BaseSearch;
import br.ufg.labtime.ponto.model.type.Situation;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSearch extends BaseSearch {
    private String name;

    private String email;

    private String query;

    private Situation situation;

}
