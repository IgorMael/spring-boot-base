package br.ufg.labtime.ponto.service;

import br.ufg.labtime.ponto.dto.user.UserSearch;
import br.ufg.labtime.ponto.dto.user.UserSpec;
import br.ufg.labtime.ponto.model.User;
import br.ufg.labtime.ponto.model.type.Situation;
import br.ufg.labtime.ponto.repository.UserRepository;
import br.ufg.labtime.ponto.system.SessionControl;
import br.ufg.labtime.ponto.system.exception.BusinessException;
import br.ufg.labtime.ponto.system.exception.ErrorResponseHttp;
import br.ufg.labtime.ponto.utils.Utils;
import br.ufg.labtime.ponto.validation.ProgrammaticallyValidatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final ProgrammaticallyValidatingService programmaticallyValidatingService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //Save
    public User save(User user) {
        verify(user);
        user.setId(0L);
        user.setEmail(user.getEmail().trim().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User update(User saveUser, long id) throws NoSuchMethodException, MethodArgumentNotValidException {
        User userDB = getById(id);

        if (!Utils.isNullOrEmpty(saveUser.getName())) {
            userDB.setName(saveUser.getName());
        }

        if (!Utils.isNullOrEmpty(saveUser.getEmail())) {
            userDB.setEmail(saveUser.getEmail());
        }
        User userEmail = userRepository.findByEmail(saveUser.getEmail());

        programmaticallyValidatingService.validateEmailWithId(userEmail != null ? userEmail.getEmail() : null,
                userEmail != null ? userEmail.getId() : null,
                saveUser.getId());
        if (!Utils.isNullOrEmpty(saveUser.getPassword())) {
            programmaticallyValidatingService.validatePassword(saveUser.getPassword());
            userDB.setPassword(bCryptPasswordEncoder.encode(saveUser.getPassword()));
        }
        verify(userDB);
        return userRepository.save(userDB);
    }

    public User detail(Long id) {
        return getById(id);
    }

    public void delete(long id) {
        Long sessionId = SessionControl.getIdUserLogged();
        if (Utils.isNullOrEmpty(sessionId)) {
            throw new BusinessException(Collections.singletonList(new ErrorResponseHttp("Access denied.")));
        }
        User user = getById(id);
        if (user.getId().equals(sessionId)) {
            throw new BusinessException(Collections.singletonList(new ErrorResponseHttp("Access denied.")));
        }
        user.setSituation(!user.getSituation().equals(Situation.ACTIVE) ? Situation.ACTIVE : Situation.INACTIVE);
        userRepository.save(user);
    }

    public Page<User> getPage(UserSearch userSearch) {

        Page<User> userPage = userRepository.findAll(UserSpec.getAll(userSearch),
                PageRequest.of(userSearch.getPage(), userSearch.getQuantity(), Sort.by("name").ascending()));

        return userPage;
    }


    private User getById(long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new BusinessException(Collections.singletonList(new ErrorResponseHttp("User not found")));
        }

        return user.get();
    }

    User getByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    public User changeADM(Long id) {
        User user = getById(id);
        user.setAdm(!user.getAdm());

        userRepository.changeAdm(user.getId(), user.getAdm());
        return user;
    }

    public void alterSituation(long id, Situation situation) {
        userRepository.changeSituation(getById(id).getId(), situation);
    }

    public User toggleAdmin(Long id) {
        User user = getById(id);
        user.setAdm(!user.getAdm());

        userRepository.changeAdm(user.getId(), user.getAdm());
        return user;
    }


    private void verifyAuthority(Long id) {
        Long idUserLogged = SessionControl.getIdUserLogged();

        if (Utils.isNullOrEmpty(idUserLogged)) {
            throw new BusinessException(Collections.singletonList(new ErrorResponseHttp("Access denied.")));
        } else {
            if (!id.equals(idUserLogged)) {
                throw new BusinessException(Collections.singletonList(new ErrorResponseHttp("Access denied! No privileges.")));
            }
        }
    }

    private void verify(User user) {
        if (Utils.isNullOrEmpty(user.getEmail())) {
            throw new BusinessException(Collections.singletonList(new ErrorResponseHttp("Invalid user.")));
        }
        if (Utils.isNullOrEmpty(user.getName())) {
            throw new BusinessException(Collections.singletonList(new ErrorResponseHttp("Invalid user.")));
        }

    }


}
