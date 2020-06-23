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
    // This method is defined in Spring security interface UserDetailsService
    public UserDetails loadUserByUsername(String displayName) throws UsernameNotFoundException {
        // Security implementation stores display name in JWT token, so going to load details by display name
        return loadHourglassUserByDisplayName(displayName);
    }

    public HourglassUser loadHourglassUserByDisplayName(String displayName) throws UsernameNotFoundException {
        User user = userRepository.getUserByDisplayName(displayName);
        if(user == null) {
            throw new AccessDeniedException("Incorrect credentials.");
        }

        return HourglassUser.hourglassUserBuilder()
                .username(user.getDisplayName())
                .password("HIDDEN")
                .authorities("USER")
                .minJwtIssuedTimestamp(user.getMinJwtIssuedTimestamp())
                .build();
    }
}
