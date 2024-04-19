package ru.mirea.pr_19.services;

import org.springframework.transaction.annotation.Transactional;

import ru.mirea.pr_19.dto.UniversityDTO;

import java.util.List;

public interface UniversityService {
    @Transactional(readOnly = true)
    List<UniversityDTO> getUniversities();
    @Transactional(readOnly = true)
    UniversityDTO getUniversityById(Long universityId);
    @Transactional
    UniversityDTO addUniversity(UniversityDTO universityDTO);
    @Transactional
    void deleteUniversityById(Long id);
    @Transactional
    void deleteAll();
    @Transactional(readOnly = true)
    List<UniversityDTO> getFilteredUniversities(String filteredBy, String value);
}
