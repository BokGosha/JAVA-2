package ru.mirea.pr_19.components.impl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import ru.mirea.pr_19.components.*;
import ru.mirea.pr_19.models.*;
import ru.mirea.pr_19.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final UniversityRepository universityRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public University getUniversityByStudent(Long studentId) {
        log.info("Find university by student with id {}", studentId);

        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student "
                + "with this id not found")).getUniversity();
    }

    @Override
    public List<Student> getStudents() {
        log.info("Find all students");

        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long studentId) {
        log.info("Find student with id {}", studentId);

        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public Student addStudent(String firstName, String middleName, String lastName) {
        log.info("Add student with firstname {}, middlename {}, lastname {}", firstName, middleName, lastName);

        Student student = new Student();

        student.setFirstName(firstName);
        student.setMiddleName(middleName);
        student.setLastName(lastName);

        String message = "Student " + firstName + " was saved";
        emailService.sendEmail(message);

        return studentRepository.save(student);
    }

    @Override
    public Student deleteStudentById(Long id) {
        log.info("Delete student with id {}", id);

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            studentRepository.delete(student);

            return student;
        }

        return null;
    }

    @Override
    public void deleteAll() {
        log.info("Delete all students");

        studentRepository.deleteAll();
    }

    @Override
    public void addStudentToUniversity(Student student, University university) {
        log.info("Add student with id {} to university with id {}", student.getId(), university.getId());

        student.setUniversity(university);

        university.getStudents().add(student);

        String message = "Student with firstName " + student.getFirstName() + " was saved";
        emailService.sendEmail(message);

        universityRepository.saveAndFlush(university);
        studentRepository.save(student);
    }

    @Override
    public List<Student> filterStudentsByName(String name) {
        log.info("Find students with name {}", name);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);

        Root<Student> root = query.from(Student.class);

        Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstName"), "%" + name + "%");
        Predicate middleNamePredicate = criteriaBuilder.like(root.get("middleName"), "%" + name + "%");
        Predicate lastNamePredicate = criteriaBuilder.like(root.get("lastName"), "%" + name + "%");

        query.select(root)
                .where(criteriaBuilder.or(firstNamePredicate, middleNamePredicate, lastNamePredicate));

        return entityManager.createQuery(query).getResultList();
    }
}
