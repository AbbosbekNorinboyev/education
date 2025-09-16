package uz.pdp.education.service;

import uz.pdp.education.dto.request.LoginRequest;
import uz.pdp.education.dto.request.RegisterRequest;
import uz.pdp.education.dto.response.Response;

public interface AuthUserService {
    Response<?> register(RegisterRequest registerRequest);

    Response<?> login(LoginRequest loginRequest);
}
