package ru.mirea.pr_19.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import ru.mirea.pr_19.dto.StudentDTO;
import ru.mirea.pr_19.services.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<StudentDTO> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/register")
    public StudentDTO registerStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.addStudent(studentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }

    @DeleteMapping("/all")
    public void deleteUniversityById() {
        studentService.deleteAll();
    }

    @GetMapping("/filtered")
    public List<StudentDTO> getStudents(@RequestParam String filteredBy, @RequestParam String value) {
        return studentService.getFilteredStudents(filteredBy, value);
    }
}
