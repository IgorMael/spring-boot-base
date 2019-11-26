package br.ufg.labtime.ponto.model;

import br.ufg.labtime.ponto.model.type.Situation;

public interface IUser {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    Situation getSituation();

    void setSituation(Situation situation);

    Boolean getAdm();

    void setAdm(Boolean adm);
}
