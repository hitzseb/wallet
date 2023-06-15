package com.hitzseb.wallet.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitzseb.wallet.service.RegistrationService;
import com.hitzseb.wallet.dto.Credentials;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

	private final RegistrationService registrationService;

	@Operation(summary = "Creates a new user.",
			description = "Creates a new user account that remains inactive until" +
					" their email address is confirmed through a confirmation email" +
					" sent to the provided email address.")
	@PostMapping
	public String register(@RequestBody Credentials request) {
		return registrationService.register(request);
	}

	@Operation(summary = "Enables a newly registered user.",
			description = "Activates a newly registered user account," +
					" given a valid and non-expired confirmation token.")
	@GetMapping(path = "confirm")
	public String confirm(@RequestParam("token") String token) {
		return registrationService.confirmToken(token);
	}

}
