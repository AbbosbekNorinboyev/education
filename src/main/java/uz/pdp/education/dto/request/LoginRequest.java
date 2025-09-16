package uz.pdp.education.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginRequest {
    @NotBlank(message = "username can not be null or empty")
    private String username;
    @NotBlank(message = "password can not be null or empty")
    private String password;
}
