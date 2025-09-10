package uz.pdp.education.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.pdp.education.entity.AuthUser;

public class AuthUserSpecification {
    public static Specification<AuthUser> hasFullName(String fullName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("fullName"), "%" + fullName + "%");
    }

    public static Specification<AuthUser> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%");
    }

    public static Specification<AuthUser> hasUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("username"), "%" + username + "%");
    }
}
