package ru.mirea.pr_19.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ru.mirea.pr_19.entities.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentsByFirstNameEquals(String value);
    List<Student> findStudentsByMiddleNameEquals(String value);
    List<Student> findStudentsByLastNameEquals(String value);
}
