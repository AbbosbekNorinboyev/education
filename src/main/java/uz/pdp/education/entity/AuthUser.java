package uz.pdp.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.education.enums.Role;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "auth_user")
public class AuthUser extends BaseEntity {
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
