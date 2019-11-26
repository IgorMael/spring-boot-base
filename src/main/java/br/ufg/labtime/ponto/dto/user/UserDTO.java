package br.ufg.labtime.ponto.dto.user;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDTO {

    @Expose
    private Long id;

    @Expose
    @NotNull(message = "name must not be null")
    @Size(max = 300, message = "Name with a maximum of 300 characters.")
    private String name;

}