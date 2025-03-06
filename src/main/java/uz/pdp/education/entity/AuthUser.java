package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.education.enums.Role;
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
