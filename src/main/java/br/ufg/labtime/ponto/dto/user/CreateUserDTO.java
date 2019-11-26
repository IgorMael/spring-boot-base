package br.ufg.labtime.notifica.dto.user;

import br.ufg.labtime.notifica.model.type.Situation;
import br.ufg.labtime.notifica.validation.UniqueEmail;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserDTO {
    @Expose
    private Long id;

    @Expose
    @NotNull(message = "name must not be null")
    @Size(max = 300, message = "Name with a maximum of 300 characters.")
    private String name;

    @NotNull(message = "ME01")
    @Email(message = "Email is not valid")
    @Size(max = 100, message = "Email maximum 100 characters.")
    @UniqueEmail(message = "Email is already used")
    private String email;

    @NotNull(message = "ME01")
    @Size(min = 6, max = 15, message = "6-character minimum password length")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?!.*\\s).*$", message = "Invalid password")
    private String password;

    @Expose(deserialize = false)
    private Situation situation = Situation.ACTIVE;
}
