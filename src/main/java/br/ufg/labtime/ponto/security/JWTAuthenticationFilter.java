package br.ufg.labtime.ponto.security;

import br.ufg.labtime.ponto.dto.security.AuthenticationDTO;
import br.ufg.labtime.ponto.dto.security.DetailAuthenticationDTO;
import br.ufg.labtime.ponto.model.IUser;
import br.ufg.labtime.ponto.model.User;
import br.ufg.labtime.ponto.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private ModelMapper modelMapper;

    private JWTUtils jwtUtils;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
        this.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        try { //Dentro do Request tem os dados do User. Vamos pegar o user do request
            IUser user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            request.setAttribute("email", user.getEmail());
            request.setAttribute("name", user.getName());
            request.setAttribute("adm", user.getAdm());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail().toLowerCase().trim(), user.getPassword());
            return this.authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        AuthenticationDTO authenticationDTO = modelMapper.map(authResult.getPrincipal(), AuthenticationDTO.class);

        String token = jwtUtils.generateToken(authenticationDTO);

        DetailAuthenticationDTO user = new DetailAuthenticationDTO();

        user.setId(authenticationDTO.getId());
        user.setEmail(authenticationDTO.getUsername());
        user.setName(authenticationDTO.getName());
        user.setAdm(authenticationDTO.getAdm());
        user.setAuthentication(token);

        response.getWriter().write(new GsonBuilder().create().toJson(new ResponseEntity<>(user, HttpStatus.OK)));
        response.addHeader("Authorization", token);
        response.addHeader("access-control-expose-headers", "Authorization");
    }


}