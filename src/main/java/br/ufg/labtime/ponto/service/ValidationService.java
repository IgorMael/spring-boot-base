package br.ufg.labtime.ponto.service;

import br.ufg.labtime.ponto.model.User;
import br.ufg.labtime.ponto.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final UserService userService;

    public boolean uniqueEmail(String email) {
        User user = this.userService.getByEmail(email);
        return Utils.isNullOrEmpty(user);
    }

}
