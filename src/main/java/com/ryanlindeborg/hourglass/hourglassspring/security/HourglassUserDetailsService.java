package com.ryanlindeborg.hourglass.hourglassspring.security;

import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class HourglassUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.getUserByDisplayName(s);
        if(user == null) {
            throw new AccessDeniedException("Incorrect credentials.");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getDisplayName())
                .password("HIDDEN")
                .authorities("USER")
                .build();
    }
}
