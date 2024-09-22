package com.fullcycle.subscription.infrastructure.authentication.principal;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public class CodeflixAuthentication extends AbstractAuthenticationToken {

    private final Jwt jwt;
    private final CodeflixUser user;

    public CodeflixAuthentication(Jwt jwt, CodeflixUser user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.jwt = jwt;
        this.user = user;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
