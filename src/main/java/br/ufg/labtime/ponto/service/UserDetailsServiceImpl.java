package br.ufg.labtime.ponto.service;


import br.ufg.labtime.ponto.model.IUser;
import br.ufg.labtime.ponto.repository.UserRepository;
import br.ufg.labtime.ponto.security.UserSpringSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        IUser programmer = userRepository.findByEmail(username);

        if (programmer != null) {
            List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
            List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");

            return new UserSpringSecurity(programmer.getId(),
                    programmer.getEmail(),
                    programmer.getPassword(),
                    programmer.getName(),
                    programmer.getAdm(),
                    programmer.getAdm() ? authorityListAdmin : authorityListUser);
        }

        throw new UsernameNotFoundException("User with email: " + username + " not found");
    }
}
