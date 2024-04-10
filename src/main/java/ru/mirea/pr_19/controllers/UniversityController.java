package ru.mirea.pr_19.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import ru.mirea.pr_19.components.impl.UniversityServiceImpl;
import ru.mirea.pr_19.models.University;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/universities")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityServiceImpl universityService;

    @PostMapping
    public @ResponseBody ResponseEntity<University> createUniversity(@RequestBody University request) {
        University university = universityService.addUniversity(request.getName(), request.getCreationDate());

        return ResponseEntity.status(HttpStatus.CREATED).body(university);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<?> getUniversity(@PathVariable Long id) {
        University university = universityService.getUniversityById(id);

        if (university == null) {
            return new ResponseEntity<>("University{\"id\": " + id + "} was not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(university, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<University>> getAllUniversities() {
        List<University> universities = universityService.getUniversities();

        if (universities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(universities);
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<?> deleteUniversity(@PathVariable Long id) {
        University university = universityService.deleteUniversityById(id);

        if (university == null) {
            return new ResponseEntity<>("University{\"id\": " + id + "} was not found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok("University{\"id\": " + id + "} was deleted");
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<String> deleteAllUniversities() {
        universityService.deleteAll();

        return ResponseEntity.ok("All universities were deleted");
    }

    @GetMapping("/filter/name")
    public ResponseEntity<List<University>> filterUniversitiesByName(@RequestParam("name") String name) {
        List<University> filteredUniversities = universityService.filterUniversitiesByName(name);

        return ResponseEntity.ok(filteredUniversities);
    }

    @GetMapping("/filter/creationDate")
    public ResponseEntity<List<University>> filterUniversitiesByDate(@RequestParam("date") LocalDate date) {
        List<University> filteredUniversities = universityService.filterUniversitiesByDate(date);

        return ResponseEntity.ok(filteredUniversities);
    }
}