package ru.mirea.pr_19.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import ru.mirea.pr_19.components.impl.*;
import ru.mirea.pr_19.models.*;

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentService;
    private final UniversityServiceImpl universityService;

    @PostMapping
    public @ResponseBody ResponseEntity<Student> createStudent(@RequestBody Student request) {
        Student student = studentService.addStudent(request.getFirstName(), request.getMiddleName(), request.getLastName());

        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @GetMapping(value = "/{id}/university")
    public @ResponseBody University getStudentUniversity(@PathVariable Long id) {
        return studentService.getUniversityByStudent(id);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<?> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);

        if (student == null) {
            return new ResponseEntity<>("Student{\"id\": " + id + "} was not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getStudents();

        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(students);
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        Student student = studentService.deleteStudentById(id);

        if (student == null) {
            return new ResponseEntity<>("Student{\"id\": " + id + "} was not found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok("Student{\"id\": " + id + "} was deleted");
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<String> deleteAllStudents() {
        studentService.deleteAll();

        return ResponseEntity.ok("All students were deleted");
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody Student student,
                                           @RequestParam("universityId") Long universityId) {
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        University university = universityService.getUniversityById(universityId);

        if (university == null) {
            return ResponseEntity.notFound().build();
        }

        studentService.addStudentToUniversity(student, university);

        return ResponseEntity.ok(student);
    }

    @GetMapping("/filter/firstName")
    public ResponseEntity<List<Student>> filterStudentsByFirstName(@RequestParam("name") String name) {
        List<Student> filteredStudents = studentService.filterStudentsByName(name);

        return ResponseEntity.ok(filteredStudents);
    }

    @GetMapping("/filter/middleName")
    public ResponseEntity<List<Student>> filterStudentsByMiddleName(@RequestParam("name") String name) {
        List<Student> filteredStudents = studentService.filterStudentsByName(name);

        return ResponseEntity.ok(filteredStudents);
    }

    @GetMapping("/filter/lastName")
    public ResponseEntity<List<Student>> filterStudentsByLastName(@RequestParam("name") String name) {
        List<Student> filteredStudents = studentService.filterStudentsByName(name);

        return ResponseEntity.ok(filteredStudents);
    }
}
