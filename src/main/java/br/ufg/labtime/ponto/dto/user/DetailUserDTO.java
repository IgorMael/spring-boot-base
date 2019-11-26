package br.ufg.labtime.ponto.dto.user;

import br.ufg.labtime.ponto.model.User;
import br.ufg.labtime.ponto.model.type.Situation;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class DetailUserDTO extends User {
    private Long id;

    private String email;

    private Situation situation;

    private Boolean adm;

    private Map<String, Object> mapAttributes;

    @JsonAnyGetter
    public Map<String, Object> getMapAttributes() {
        return mapAttributes;
    }
}
