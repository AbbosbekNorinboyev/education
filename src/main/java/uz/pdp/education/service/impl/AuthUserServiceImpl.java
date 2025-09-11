package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.pdp.education.config.CustomUserDetailsService;
import uz.pdp.education.dto.LoginRequest;
import uz.pdp.education.dto.RegisterRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.enums.Role;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.service.AuthUserService;
import uz.pdp.education.utils.JWTUtil;

import java.time.LocalDateTime;
import java.util.Optional;

import static uz.pdp.education.utils.PasswordHasher.hashPassword;
import static uz.pdp.education.utils.PasswordValidator.validatePassword;
import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final JWTUtil jwtUtil;
    private final AuthUserRepository authUserRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Response<?> register(RegisterRequest registerRequest) {
        Optional<AuthUser> byUsername = authUserRepository.findByUsername(registerRequest.getUsername());
        if (byUsername.isPresent()) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .message("Username already exists")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        AuthUser authUser = new AuthUser();
        authUser.setFullName(registerRequest.getFullName());
        authUser.setUsername(registerRequest.getUsername());
        authUser.setPassword(hashPassword(registerRequest.getPassword()));
        authUser.setRole(Role.USER);
        authUserRepository.save(authUser);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AuthUser successfully register")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> login(LoginRequest loginRequest) {
        AuthUser authUser = authUserRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found by username: " + loginRequest.getUsername()));
        if (authUser.getUsername() == null) {
            return Response.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND)
                    .success(false)
                    .message("Username not found")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        if (!validatePassword(loginRequest.getPassword(), authUser.getPassword())) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .message("Invalid password")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message(jwtToken)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
