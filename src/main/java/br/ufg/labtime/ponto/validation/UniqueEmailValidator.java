package br.ufg.labtime.ponto.validation;


import br.ufg.labtime.ponto.model.User;
import br.ufg.labtime.ponto.repository.UserRepository;
import br.ufg.labtime.ponto.utils.Utils;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User userDB = userRepository.findByEmail(value);
        return Utils.isNullOrEmpty(value) || Utils.isNullOrEmpty(userDB);
    }
}
