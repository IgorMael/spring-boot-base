package br.ufg.labtime.notifica.dto.user;

import br.ufg.labtime.notifica.dto.BaseSearch;
import br.ufg.labtime.notifica.model.type.Situation;
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
