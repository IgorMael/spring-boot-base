package br.ufg.labtime.ponto.security;


import br.ufg.labtime.ponto.dto.security.AuthenticationDTO;
import br.ufg.labtime.ponto.utils.JWTUtils;
import br.ufg.labtime.ponto.utils.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTUtils jwtUtils;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtil) {
        super(authenticationManager);
        this.jwtUtils = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if (!Utils.isNullOrEmpty(token)) {
            UsernamePasswordAuthenticationToken auth = getUserAuthenticaticatedToken(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getUserAuthenticaticatedToken(String token) {
        if (jwtUtils.validToken(token)) {
            AuthenticationDTO user = jwtUtils.getUser(token);
            if (user == null) {
                return null;
            }
            List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
            List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");

            UserDetails userDetails = new UserSpringSecurity(user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getAdm(),
                    user.getAuthorities().contains("ROLE_ADMIN") ? authorityListAdmin : authorityListUser);

            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        return null;
    }
}
