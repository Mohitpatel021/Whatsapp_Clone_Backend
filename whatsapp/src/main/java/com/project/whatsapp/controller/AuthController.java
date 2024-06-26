package com.project.whatsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.whatsapp.Exception.UserException;
import com.project.whatsapp.Response.AuthResponse;
import com.project.whatsapp.ServiceImpl.CustomUserService;
import com.project.whatsapp.config.TokenProvider;
import com.project.whatsapp.modal.User;
import com.project.whatsapp.repository.UserRepository;
import com.project.whatsapp.request.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private CustomUserService customUserService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String full_name = user.getFullName();
		String password = user.getPassword();
		User isUser = userRepository.findByEmail(email);
		if (isUser != null) {
			throw new UserException("Email is already used !! Please Use another email.. " + email);
		}
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFullName(full_name);
		createdUser.setPassword(passwordEncoder.encode(password));
		userRepository.save(createdUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse(jwt, true);
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest) {

		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse(jwt, true);
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Username or Password");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Username or Password");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
