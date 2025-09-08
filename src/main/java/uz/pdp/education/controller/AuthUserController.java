package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.education.dto.LoginRequest;
import uz.pdp.education.dto.RegisterRequest;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.enums.Role;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.config.CustomUserDetailsService;
import uz.pdp.education.utils.JWTUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthUserController {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetails;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        Optional<AuthUser> byUsername = authUserRepository.findByUsername(registerRequest.getUsername());
        if (byUsername.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        AuthUser authUser = new AuthUser();
        authUser.setUsername(registerRequest.getUsername());
        authUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        authUser.setRole(Role.USER);
        authUserRepository.save(authUser);
        return ResponseEntity.ok("AuthUser successfully register");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        AuthUser authUser = authUserRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("AuthUser not found by username: " + loginRequest.getUsername()));
        if (authUser.getUsername() == null) {
            return ResponseEntity.ok().body("Username not found");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        UserDetails userDetails = customUserDetails.loadUserByUsername(loginRequest.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(jwtToken);
    }
}
