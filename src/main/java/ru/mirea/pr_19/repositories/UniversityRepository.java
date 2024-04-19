package ru.mirea.pr_19.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ru.mirea.pr_19.entities.Student;
import ru.mirea.pr_19.entities.University;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long>, JpaSpecificationExecutor<University> {
    List<University> findUniversitiesByNameEquals(String value);
    List<University> findUniversitiesByCreationDateEquals(String creationDate);
}
