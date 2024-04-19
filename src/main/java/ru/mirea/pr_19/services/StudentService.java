package ru.mirea.pr_19.services;

import org.springframework.transaction.annotation.Transactional;

import ru.mirea.pr_19.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    @Transactional(readOnly = true)
    List<StudentDTO> getStudents();
    @Transactional(readOnly = true)
    StudentDTO getStudentById(Long studentId);
    @Transactional
    StudentDTO addStudent(StudentDTO studentDTO);
    @Transactional
    void deleteStudentById(Long id);
    @Transactional
    void deleteAll();
    @Transactional(readOnly = true)
    List<StudentDTO> getFilteredStudents(String filteredBy, String value);
}
