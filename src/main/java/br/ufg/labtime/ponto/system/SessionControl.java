package br.ufg.labtime.ponto.system;

import br.ufg.labtime.ponto.security.UserSpringSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionControl {
    public static Long getIdUserLogged() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return ((UserSpringSecurity) authentication.getPrincipal()).getId();
    }

    public static String getEmailUserLogged() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return ((UserSpringSecurity) authentication.getPrincipal()).getUsername();
    }

    public static boolean getAuthorities(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        }
        UserSpringSecurity principal = (UserSpringSecurity) authentication.getPrincipal();
        return ((UserSpringSecurity) authentication.getPrincipal())
                .getAuthorities()
                .contains(new SimpleGrantedAuthority(role));
    }

}
