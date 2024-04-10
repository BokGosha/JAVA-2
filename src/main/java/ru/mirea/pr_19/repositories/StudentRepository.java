package ru.mirea.pr_19.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mirea.pr_19.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
