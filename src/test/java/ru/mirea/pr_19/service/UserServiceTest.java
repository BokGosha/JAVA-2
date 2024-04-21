package ru.mirea.pr_19.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.mirea.pr_19.dto.UserDTO;
import ru.mirea.pr_19.entities.UserEntity;
import ru.mirea.pr_19.repositories.UserRepository;
import ru.mirea.pr_19.services.impl.UserServiceImpl;

public class UserServiceTest {
    @Test
    @DisplayName("Тестирование UserService#loadUserByUsername")
    public void loadUserByUsernameShouldReturnUser() {
        var passwordEncoder = Mockito.mock(PasswordEncoder.class);
        var userRepository = Mockito.mock(UserRepository.class);

        var userService = new UserServiceImpl(
                passwordEncoder, userRepository
        );

        Mockito.when(userRepository.getByUsername("user")).thenReturn(new UserEntity("user", "password"));

        var user = userService.loadUserByUsername("user");
        Assertions.assertThat(user.getUsername()).isEqualTo("user");
    }

    @Test
    @DisplayName("Тестирование UserService#saveUser")
    public void saveUserShouldSaveUser() {
        var passwordEncoder = Mockito.mock(PasswordEncoder.class);
        var userRepository = Mockito.mock(UserRepository.class);

        var userService = new UserServiceImpl(
                passwordEncoder,
                userRepository
        );

        userService.saveUser(new UserDTO("user", "password"));

        Mockito.verify(passwordEncoder).encode("password");
        Mockito.verify(userRepository).save(Mockito.any(UserEntity.class));
    }
}
