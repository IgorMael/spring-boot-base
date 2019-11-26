package br.ufg.labtime.ponto.model;

import br.ufg.labtime.ponto.model.type.Situation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
@SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
@Getter
@Setter
public class User extends BaseModel implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @Column(name = "co_user")
    private Long id;

    @Column(name = "no_name", nullable = false, length = 300)
    private String name;

    @Column(name = "ds_email", length = 100, unique = true)
    private String email;

    @Column(name = "ds_password", length = 100)
    private String password;

    @Column(name = "st_situation")
    private Situation situation = Situation.ACTIVE;

    @Column(name = "vl_adm", updatable = false)
    private Boolean adm = false;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }
}

