package uz.pdp.education.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordValidator {
    private PasswordValidator() {
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static boolean validatePassword(String rawToken, String hashedToken) {
        return encoder.matches(rawToken, hashedToken);
    }
}
