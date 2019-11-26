package br.ufg.labtime.ponto.dto.user;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Setter
@Getter
public class EditUserDTO {
    @Expose
    private Long id;

    @Expose
    @Size(max = 300, message = "Name with a maximum of 300 characters.")
    private String name;

    @Expose
    @Email(message = "Email is not valid")
    @Size(max = 100, message = "Email maximum 100 characters.")
    private String email;

    @Expose(serialize = false)
    @Size(min = 6, max = 15, message = "Invalid password")
    private String password;

    @Expose(deserialize = false)
    private Boolean adm;
}
