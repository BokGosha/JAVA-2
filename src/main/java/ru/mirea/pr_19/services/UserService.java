package ru.mirea.pr_19.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.mirea.pr_19.dto.UserDTO;

public interface UserService extends UserDetailsService {
    void saveUser(UserDTO user);
}
