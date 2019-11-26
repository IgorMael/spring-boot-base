package br.ufg.labtime.ponto.utils;

import br.ufg.labtime.ponto.model.User;
import br.ufg.labtime.ponto.security.UserSpringSecurity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MockUtils {

    public static <T extends Enum<T>> T getSample(Class<T> aValues) {
        Random r = new Random();
        T[] values = aValues.getEnumConstants();
        return values[r.nextInt(values.length)];
    }

    public static User createUser() {
        Random r = new Random();
        User user = new User();
        user.setName(createRandomString(20));
        user.setEmail(createRandomString(40));
        user.setAdm(r.nextBoolean());
        user.setId((long) r.nextInt(999));
        return user;
    }


    public static String createRandomString(int size) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        while (uuid.length() < size) uuid = uuid.concat(UUID.randomUUID().toString().replace("-", ""));
        return uuid.substring(0, size);
    }

    public static Authentication createAuthentication(Boolean adm) {
        Random r = new Random();
        boolean userAdm = !Utils.isNullOrEmpty(adm) ? adm : r.nextBoolean();
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                createUserDetails(userAdm),
                null,
                userAdm ? authorityListAdmin : authorityListUser);
        return authentication;
    }

    public static UserDetails createUserDetails(Boolean adm) {
        Random r = new Random();
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        boolean userAdm = !Utils.isNullOrEmpty(adm) ? adm : r.nextBoolean();
        UserDetails userDetails = new UserSpringSecurity(
                (long) r.nextInt(100),
                createRandomString(40),
                createRandomString(20),
                createRandomString(20),
                userAdm,
                userAdm ? authorityListAdmin : authorityListUser);
        return userDetails;
    }
}
