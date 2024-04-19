package ru.mirea.pr_19.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.mirea.pr_19.dto.UniversityDTO;
import ru.mirea.pr_19.entities.Student;
import ru.mirea.pr_19.services.*;
import ru.mirea.pr_19.entities.University;
import ru.mirea.pr_19.repositories.UniversityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;
    private final EmailService emailService;

    @Override
    public List<UniversityDTO> getUniversities() {
        log.info("Find all universities");

        return universityRepository.findAll().stream().map(University::toDto).toList();
    }

    @Override
    public UniversityDTO getUniversityById(Long universityId) {
        log.info("Find university with id {}", universityId);

        return universityRepository.findById(universityId).map(University::toDto).orElse(null);
    }

    @Override
    public UniversityDTO addUniversity(UniversityDTO universityDTO) {
        log.info("Add university {}", universityDTO);

        University university = new University();

        university.setName(universityDTO.getName());
        university.setCreationDate(universityDTO.getCreationDate());

        //String message = "University " + university.getName() + " was saved";
        //emailService.sendEmail(message);

        universityRepository.save(university);

        return universityDTO;
    }

    @Override
    public void deleteUniversityById(Long id) {
        log.info("Delete university with id {}", id);

        universityRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        log.info("Delete all universities");

        universityRepository.deleteAll();
    }

    @Override
    public List<UniversityDTO> getFilteredUniversities(String filteredBy, String value) {
        log.info("Find universities filtered by {} with value {}", filteredBy, value);

        var entities = switch (filteredBy) {
            case "name" -> universityRepository.findUniversitiesByNameEquals(value);
            case "date" -> universityRepository.findUniversitiesByCreationDateEquals(value);
            default -> throw new IllegalStateException("Unexpected value: " + filteredBy);
        };

        return entities.stream().map(University::toDto).toList();
    }
}
