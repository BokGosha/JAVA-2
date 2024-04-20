package ru.mirea.pr_19.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.mirea.pr_19.dto.UserDTO;
import ru.mirea.pr_19.services.UserService;

@RequestMapping("/register")
@RequiredArgsConstructor
@Controller
public class RegistrationController {
    private final UserService userService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO("",""));

        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserDTO userDTO) {
        userService.saveUser(userDTO);

        return "redirect:/register?success";
    }
}
