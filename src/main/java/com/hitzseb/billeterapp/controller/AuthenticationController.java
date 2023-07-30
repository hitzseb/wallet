package com.hitzseb.billeterapp.controller;

import com.hitzseb.billeterapp.response.AuthenticationResponse;
import com.hitzseb.billeterapp.service.AuthenticationService;
import com.hitzseb.billeterapp.dto.Credential;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:4200/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Credential credential) {
        return ResponseEntity.ok(authenticationService.register(credential));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody Credential credential) {
        return ResponseEntity.ok(authenticationService.authenticate(credential));
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authenticationService.confirmToken(token);
    }

}
