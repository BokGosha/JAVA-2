package ru.mirea.pr_19.components;

import org.springframework.transaction.annotation.Transactional;

import ru.mirea.pr_19.models.University;

import java.time.LocalDate;
import java.util.List;

public interface UniversityService {
    @Transactional(readOnly = true)
    List<University> getUniversities();
    @Transactional(readOnly = true)
    University getUniversityById(Long universityId);
    @Transactional
    University addUniversity(String name, LocalDate date);
    @Transactional
    University deleteUniversityById(Long id);
    @Transactional
    void deleteAll();
    @Transactional(readOnly = true)
    List<University> filterUniversitiesByName(String name);
    @Transactional(readOnly = true)
    List<University> filterUniversitiesByDate(LocalDate date);
}
