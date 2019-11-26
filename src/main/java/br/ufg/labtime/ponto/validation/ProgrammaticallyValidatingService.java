package br.ufg.labtime.ponto.validation;

import br.ufg.labtime.ponto.dto.user.EditUserDTO;
import br.ufg.labtime.ponto.utils.Utils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class ProgrammaticallyValidatingService {

    public void validateEmailWithId(String email, Long id1, long id) throws NoSuchMethodException, MethodArgumentNotValidException {
        if (!Utils.isNullOrEmpty(email) && id1 != id) {
            genericException(EditUserDTO.class, "Email is already used");
        }
    }

    public void validatePassword(String password) throws NoSuchMethodException, MethodArgumentNotValidException {
        if (!Utils.isValidPassword(password)) {
            genericException(EditUserDTO.class, "Invalid password");
        }
    }

    public void genericException(Class<?> object, String message) throws NoSuchMethodException, MethodArgumentNotValidException {
        BindingResult bindingResult = new BeanPropertyBindingResult(object, object.getSimpleName());
        bindingResult.addError(new ObjectError(object.getSimpleName(), message));

        throw new MethodArgumentNotValidException(new MethodParameter(object.getConstructor(), -1), bindingResult);
    }

    public void validateId(Long id, long id1) throws NoSuchMethodException, MethodArgumentNotValidException {
        if (id != id1) {
            genericException(EditUserDTO.class, "Wrong user, id collision");
        }
    }
}
