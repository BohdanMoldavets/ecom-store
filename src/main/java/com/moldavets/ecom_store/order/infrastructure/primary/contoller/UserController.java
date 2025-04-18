package com.moldavets.ecom_store.order.infrastructure.primary.contoller;

import com.moldavets.ecom_store.order.infrastructure.primary.model.RestUser;
import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.order.service.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserApplicationService userApplicationService;

    @Autowired
    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @GetMapping("/authenticated")
    public ResponseEntity<RestUser> getAuthenticatedUser(@AuthenticationPrincipal Jwt jwtToken,
                                                         @RequestParam boolean forceResync) {

        User authenticatedUser = userApplicationService.getAuthenticatedUserWithSync(jwtToken, forceResync);
        RestUser restUser = RestUser.from(authenticatedUser);

        return new ResponseEntity<>(restUser, HttpStatus.OK);
    }
}
