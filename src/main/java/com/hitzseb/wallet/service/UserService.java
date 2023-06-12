package com.hitzseb.wallet.service;

import com.hitzseb.wallet.model.Token;
import com.hitzseb.wallet.model.User;
import com.hitzseb.wallet.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
	private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
	private final UserRepo userRepo;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final TokenService tokenService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}

	public String signUpUser(User user) {
		boolean userExists = userRepo.findByEmail(user.getEmail()).isPresent();

		if (userExists ) {
			
			User registeredUser = (User) userRepo.findByEmail(user.getEmail()).orElse(null);
			
			if (registeredUser.isEnabled() == false) {
				
				String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
				registeredUser.setPassword(encodedPassword);
				userRepo.save(registeredUser);
				
				String token = UUID.randomUUID().toString();
				Token confirmationToken = new Token(token, LocalDateTime.now(),
						LocalDateTime.now().plusMinutes(15), registeredUser);
				tokenService.saveConfirmationToken(confirmationToken);
				return token;
			}
			
			else {
				throw new IllegalStateException("email already taken");
			}
			
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);
		userRepo.save(user);

		String token = UUID.randomUUID().toString();
		Token confirmationToken = new Token(token, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15), user);
		tokenService.saveConfirmationToken(confirmationToken);

		return token;
	}

	public int enableUser(String email) {
		return userRepo.enableUser(email);
	}

}
