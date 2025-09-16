package uz.pdp.education.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.education.enums.Role;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegisterRequest {
    @NotBlank(message = "fullName can not be null or empty")
    private String fullName;
    @NotBlank(message = "username can not be null or empty")
    private String username;
    @NotBlank(message = "password can not be null or empty")
    private String password;
    @NotBlank(message = "role can not be null or empty")
    private Role role;
}
