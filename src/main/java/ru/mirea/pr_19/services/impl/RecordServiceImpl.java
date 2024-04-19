package ru.mirea.pr_19.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mirea.pr_19.repositories.StudentRepository;
import ru.mirea.pr_19.repositories.UniversityRepository;
import ru.mirea.pr_19.services.RecordService;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class RecordServiceImpl implements RecordService {
    private final StudentRepository studentRepository;
    private final UniversityRepository universityRepository;

    @Override
    public void createRecord(Long studentId, Long universityId) {
        log.info("Create record for student with id {} and university with id {}", studentId, universityId);

        var university = universityRepository.findById(universityId).orElseThrow();
        var student = studentRepository.findById(studentId).orElseThrow();

        student.setUniversity(university);

        studentRepository.save(student);
    }

    @Override
    public void removeRecord(Long studentId) {
        log.info("Remove record for student with id {}", studentId);

        var student = studentRepository.findById(studentId).orElseThrow();

        student.removeUniversity();

        studentRepository.save(student);
    }
}
