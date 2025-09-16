package uz.pdp.education.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.education.entity.AuthUser;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        // 👇 UserDetails dan ID olish
        AuthUser userDetails = (AuthUser) authentication.getPrincipal();
        return Optional.ofNullable(userDetails.getId());  // foydalanuvchi ID
    }
}
