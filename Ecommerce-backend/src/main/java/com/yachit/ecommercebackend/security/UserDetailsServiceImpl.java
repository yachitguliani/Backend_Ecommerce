package com.yachit.ecommercebackend.security;

import com.yachit.ecommercebackend.user.User;
import com.yachit.ecommercebackend.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())          // 👈 email = username
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name()) // ROLE_USER / ROLE_ADMIN
                .build();
    }
}
