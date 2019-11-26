package br.ufg.labtime.ponto.controller.users;

import br.ufg.labtime.ponto.dto.ResultListDTO;
import br.ufg.labtime.ponto.dto.user.CreateUserDTO;
import br.ufg.labtime.ponto.dto.user.DetailUserDTO;
import br.ufg.labtime.ponto.dto.user.EditUserDTO;
import br.ufg.labtime.ponto.dto.user.UserSearch;
import br.ufg.labtime.ponto.model.User;
import br.ufg.labtime.ponto.service.ModelMapperService;
import br.ufg.labtime.ponto.service.UserService;
import br.ufg.labtime.ponto.system.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapperService modelMapperService;

    @PutMapping
    public ResponseEntity<DetailUserDTO> save(@RequestBody @Valid CreateUserDTO createProgrammerDTO) {
        return new ResponseEntity<>(modelMapperService.toObject(userService.save(
                modelMapperService.toObject(createProgrammerDTO, User.class)),
                DetailUserDTO.class), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    public ResultListDTO<User, DetailUserDTO> getAll(@RequestBody UserSearch programmerSearch) {
        return new ResultListDTO<>(userService.getPage(programmerSearch),
                modelMapperService,
                DetailUserDTO.class);
    }


    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{id}")
    public DetailUserDTO update(@RequestBody @Valid EditUserDTO editProgrammerDTO,
                                @PathVariable long id) throws NoSuchMethodException, MethodArgumentNotValidException {
        return modelMapperService.toObject(userService.update(modelMapperService.toObject(editProgrammerDTO, User.class), id),
                DetailUserDTO.class);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{id}")
    public DetailUserDTO detail(@PathVariable long id) throws NoSuchMethodException, MethodArgumentNotValidException {
        return modelMapperService.toObject(userService.detail(id), DetailUserDTO.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) throws BusinessException {
        userService.delete(id);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}/admin")
    public ResponseEntity<String> toggleAdmin(@PathVariable long id) throws BusinessException {
        userService.toggleAdmin(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}