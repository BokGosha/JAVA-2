package ru.mirea.pr_19.components;

import org.springframework.transaction.annotation.Transactional;

import ru.mirea.pr_19.models.Student;
import ru.mirea.pr_19.models.University;

import java.util.List;

public interface StudentService {
    @Transactional(readOnly = true)
    University getUniversityByStudent(Long studentId);
    @Transactional(readOnly = true)
    List<Student> getStudents();
    @Transactional(readOnly = true)
    Student getStudentById(Long studentId);
    @Transactional
    Student addStudent(String firstName, String middleName, String lastName);
    @Transactional
    Student deleteStudentById(Long id);
    @Transactional
    void deleteAll();
    @Transactional
    void addStudentToUniversity(Student student, University university);
    @Transactional(readOnly = true)
    List<Student> filterStudentsByName(String name);
}
