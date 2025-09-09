package uz.pdp.education.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.education.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String fullName;
    private String phoneNumber;
    private String password;
    private String username;
    private Double balance;
    private Role role;
}
