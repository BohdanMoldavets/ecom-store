package com.moldavets.ecom_store.common.auth.service;

import com.moldavets.ecom_store.common.auth.model.Username;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Optional;

public class AuthenticatedUser {

    public static final String PREFERRED_USERNAME = "email";

    private AuthenticatedUser() {
    }

    public static Username username() {
        return optionalUsername().get();
        //todo orElseThrow exception
    }

    public static Optional<Username> optionalUsername() {
        return authentication().map(AuthenticatedUser::readPrincipal).flatMap(Username::of);
    }

    public static String readPrincipal(Authentication authentication) {
        if (authentication == null) {
            //TODO EXCEPTION
        }

        if(authentication.getPrincipal() instanceof UserDetails details) {
            return details.getUsername();
        }

        if(authentication instanceof JwtAuthenticationToken token) {
            return token.getTokenAttributes().get(PREFERRED_USERNAME).toString();
        }

        if(authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
            return oidcUser.getAttributes().get(PREFERRED_USERNAME).toString();
        }

        if(authentication.getPrincipal() instanceof String principal) {
            return principal;
        }

        throw new RuntimeException("Principal not supported");
        //todo throw exception
    }

    public static List<String> extractRolesFromToken(Jwt jwt) {
        List<LinkedTreeMap<String, String>> realmAccess =
                (List<LinkedTreeMap<String, String>>) jwt.getClaims().get("roles");
        return realmAccess.stream()
                .map(r -> r.get("key"))
                .toList();
    }

    private static Optional<Authentication> authentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }
}
