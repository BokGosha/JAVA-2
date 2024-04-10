package ru.mirea.pr_19.components.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.pr_19.components.EmailService;
import ru.mirea.pr_19.components.UniversityService;
import ru.mirea.pr_19.models.Student;
import ru.mirea.pr_19.models.University;
import ru.mirea.pr_19.repositories.StudentRepository;
import ru.mirea.pr_19.repositories.UniversityRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public List<University> getUniversities() {
        log.info("Find all universities");

        return universityRepository.findAll();
    }

    @Override
    public University getUniversityById(Long universityId) {
        log.info("Find student with id {}", universityId);

        return universityRepository.findById(universityId).orElse(null);
    }

    @Override
    public University addUniversity(String name, LocalDate date) {
        log.info("Add university with name {}", name);

        University university = new University();

        university.setName(name);
        university.setCreationDate(date);

        String message = "University " + name + " was saved";
        emailService.sendEmail(message);

        return universityRepository.save(university);
    }

    @Override
    public University deleteUniversityById(Long id) {
        log.info("Delete university with id {}", id);

        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            University university = optionalUniversity.get();

            universityRepository.delete(university);

            return university;
        }

        return null;
    }

    @Override
    public void deleteAll() {
        log.info("Delete all universities");

        universityRepository.deleteAll();
    }

    @Override
    public List<University> filterUniversitiesByName(String name) {
        log.info("Find universities with name {}", name);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<University> query = criteriaBuilder.createQuery(University.class);

        Root<University> root = query.from(University.class);

        query.select(root)
                .where(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + name + "%")));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<University> filterUniversitiesByDate(LocalDate date) {
        log.info("Find universities with date {}", date);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<University> query = criteriaBuilder.createQuery(University.class);

        Root<University> root = query.from(University.class);

        query.select(root)
                .where(criteriaBuilder.or(criteriaBuilder.equal(root.get("creationDate"), date)));

        return entityManager.createQuery(query).getResultList();
    }
}
