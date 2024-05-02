package ru.mirea.pr_19.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import ru.mirea.pr_19.dto.UniversityDTO;
import ru.mirea.pr_19.services.EmailService;
import ru.mirea.pr_19.services.UniversityService;

import java.util.List;

@RestController
@RequestMapping("/universities")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;
    private final EmailService emailService;

    @GetMapping
    public List<UniversityDTO> getUniversities() {
        return universityService.getUniversities();
    }

    @GetMapping("/{id}")
    public UniversityDTO getUniversityById(@PathVariable Long id) {
        return universityService.getUniversityById(id);
    }

    @PostMapping("/register")
    public UniversityDTO registerUniversity(@RequestBody UniversityDTO universityDTO) {
        String message = "University " + universityDTO.getName() + " was saved";
        emailService.sendEmail(message);

        return universityService.addUniversity(universityDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUniversityById(@PathVariable Long id) {
        universityService.deleteUniversityById(id);
    }

    @DeleteMapping("/all")
    public void deleteUniversityById() {
        universityService.deleteAll();
    }

    @GetMapping("/filtered")
    public List<UniversityDTO> getUniversities(@RequestParam String filteredBy, @RequestParam String value) {
        return universityService.getFilteredUniversities(filteredBy, value);
    }
}
