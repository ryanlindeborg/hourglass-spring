package com.ryanlindeborg.hourglass.hourglassspring;

import com.ryanlindeborg.hourglass.hourglassspring.model.User;
import com.ryanlindeborg.hourglass.hourglassspring.repositories.UserRepository;
import com.ryanlindeborg.hourglass.hourglassspring.services.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HourglassSpringApplication.class)
@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    ProfileService profileService;

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setFirstName("John");
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
        assertEquals(user, profileService.getUserById(1L));
    }
}
