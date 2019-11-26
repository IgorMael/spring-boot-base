package br.ufg.labtime.ponto.controller.validations;

import br.ufg.labtime.ponto.service.ValidationService;
import br.ufg.labtime.ponto.system.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validation")
@RequiredArgsConstructor
public class ValidationController {
    private final ValidationService validationService;

    @GetMapping("/unique-email/{email}")
    public boolean uniqueEmail(@PathVariable String email) throws BusinessException {
        return validationService.uniqueEmail(email);
    }

}
