package br.ufg.labtime.ponto.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserSpringSecurity implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Boolean adm;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSpringSecurity(Long id, List<GrantedAuthority> authorities) {
        this.id = id;
        this.authorities = authorities;
    }

    public UserSpringSecurity(Long id, String email, String password, Boolean adm, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.adm = adm;
        this.password = password;
        this.authorities = authorities;
    }

    public UserSpringSecurity(Long id, String email, String password, String name, Boolean adm, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.adm = adm;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }


    public UserSpringSecurity(Long id, String email, List<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Boolean getAdm() {
        return adm;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
