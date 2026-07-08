package com.ipd.Gestion_Scolaire.security;

import com.ipd.Gestion_Scolaire.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (!StringUtils.hasText(email)) {
            throw new UsernameNotFoundException("Email utilisateur obligatoire");
        }
        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);
        return userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " introuvable"));
    }
}
