package com.hitzseb.wallet.controller;

import com.hitzseb.wallet.responses.AuthenticationResponse;
import com.hitzseb.wallet.dto.Credentials;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.hitzseb.wallet.service.JwtService;
import com.hitzseb.wallet.service.UserService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("https://hitzseb-wallet-wizard.web.app/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Operation(summary = "Authenticates an user. ",
            description = "Takes a JSON with an email and a password." +
                    " If credentials are correct it returns a JWT for future requests.")
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Credentials credentials) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final String email = credentials.email();
        final UserDetails userDetails = userService.loadUserByUsername(email);
        final String jwt = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
