package ru.mirea.pr_19.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.mirea.pr_19.dto.UserDTO;
import ru.mirea.pr_19.entities.UserEntity;
import ru.mirea.pr_19.repositories.UserRepository;
import ru.mirea.pr_19.services.UserService;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = users.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(user.getUsername(), user.getPassword(), List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        ));
    }

    @Override
    public void saveUser(UserDTO user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        users.save(userEntity);
    }
}