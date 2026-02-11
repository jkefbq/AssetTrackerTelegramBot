package com.cryptodemoaccount.config.security;

import com.cryptodemoaccount.config.YamlConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminUserService implements UserDetailsService {

    private static final String USERNAME = "admin";
    private final String encodePassword;

    public AdminUserService(
            PasswordEncoder passwordEncoder,
            YamlConfig config
    ) {
        this.encodePassword = passwordEncoder.encode(config.getAdmin().getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(USERNAME)) {
            return new User(username, encodePassword,
                    Set.of(new SimpleGrantedAuthority(UserRole.ADMIN.name())));
        } else {
            return null;
        }
    }
}
