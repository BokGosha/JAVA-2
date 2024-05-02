package ru.mirea.pr_19.services.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import ru.mirea.pr_19.dto.StudentDTO;
import ru.mirea.pr_19.services.*;
import ru.mirea.pr_19.entities.*;
import ru.mirea.pr_19.repositories.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<StudentDTO> getStudents() {
        log.info("Find all students");

        return studentRepository.findAll().stream().map(Student::toDto).toList();
    }

    @Override
    public StudentDTO getStudentById(Long studentId) {
        log.info("Find student with id {}", studentId);

        return studentRepository.findById(studentId).map(Student::toDto).orElse(null);
    }

    @Override
    public StudentDTO addStudent(StudentDTO studentDTO) {
        log.info("Add student: {}", studentDTO);

        Student student = new Student();

        student.setFirstName(studentDTO.getFirstName());
        student.setMiddleName(studentDTO.getMiddleName());
        student.setLastName(studentDTO.getLastName());

        studentRepository.save(student);

        return studentDTO;
    }

    @Override
    public void deleteStudentById(Long id) {
        log.info("Delete student with id {}", id);

        studentRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        log.info("Delete all students");

        studentRepository.deleteAll();
    }

    @Override
    public List<StudentDTO> getFilteredStudents(String filteredBy, String value) {
        log.info("Find students filtered by {} with value {}", filteredBy, value);

        var entities = switch (filteredBy) {
            case "firstName" -> studentRepository.findStudentsByFirstNameEquals(value);
            case "middleName" -> studentRepository.findStudentsByMiddleNameEquals(value);
            case "lastName" -> studentRepository.findStudentsByLastNameEquals(value);
            default -> throw new IllegalStateException("Unexpected value: " + filteredBy);
        };

        return entities.stream().map(Student::toDto).toList();
    }
}
