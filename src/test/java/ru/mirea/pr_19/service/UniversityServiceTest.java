package ru.mirea.pr_19.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.mirea.pr_19.dto.UniversityDTO;
import ru.mirea.pr_19.repositories.UniversityRepository;
import ru.mirea.pr_19.services.EmailService;
import ru.mirea.pr_19.entities.University;
import ru.mirea.pr_19.services.impl.UniversityServiceImpl;

import java.util.List;
import java.util.Optional;

public class UniversityServiceTest {
    private static UniversityRepository universityRepository;
    private static List<University> entities;

    @BeforeAll
    public static void init() {
        universityRepository = Mockito.mock(UniversityRepository.class);

        entities = List.of(
                new University(0L, "МИРЭА", "22-01-1990", List.of()),
                new University(1L, "МИСИС", "22-01-1991", List.of()),
                new University(2L, "МТУСИ", "22-01-1992", List.of())
        );

        Mockito.when(universityRepository.findAll()).thenReturn(entities);
    }

    @Test
    @DisplayName("Тестирование UniversityService#getUniversities")
    public void getUniversitiesShouldReturnUniversities() {
        UniversityServiceImpl universityService = new UniversityServiceImpl(
                universityRepository
        );

        Assertions.assertThat(
                universityService.getUniversities()
        ).containsAll(entities.stream().map(University::toDto).toList());
    }

    @Test
    @DisplayName("Тестирование UniversityService#getUniversityById")
    public void getUniversityByIdShouldReturnUniversity() {
        Mockito.when(universityRepository.findById(1L)).thenReturn(Optional.ofNullable(entities.get(1)));

        UniversityServiceImpl universityService = new UniversityServiceImpl(
                universityRepository
        );

        Assertions.assertThat(
                universityService.getUniversityById(1L).getName()
        ).isEqualTo(entities.get(1).getName());
    }

    @Test
    @DisplayName("Тестирование UniversityService#addUniversity")
    public void addUniversityShouldAddUniversity() {
        UniversityServiceImpl universityService = new UniversityServiceImpl(
                universityRepository
        );

        universityService.addUniversity(new UniversityDTO(0L, "МГУ", "22-01-1990", List.of()));

        Mockito.verify(universityRepository).save(
                new University(0L, "МГУ", "22-01-1990", List.of())
        );
    }

    @Test
    @DisplayName("Тестирование UniversityService#deleteUniversityById")
    public void deleteUniversityByIdShouldDeleteUniversity() {
        UniversityServiceImpl universityService = new UniversityServiceImpl(
                universityRepository
        );

        universityService.deleteUniversityById(0L);

        Mockito.verify(universityRepository).deleteById(0L);
    }

    @Test
    @DisplayName("Тестирование UniversityService#getFilteredUniversities")
    public void getFilteredUniversitiesShouldReturnFilteredUniversities() {
        UniversityServiceImpl universityService = new UniversityServiceImpl(
                universityRepository
        );

        var filtered = entities.stream().filter(x -> x.getName().equals("МИРЭА")).toList();

        Mockito.when(universityRepository.findUniversitiesByNameEquals("МИРЭА"))
                .thenReturn(filtered);

        Assertions.assertThat(
                universityService.getFilteredUniversities("name", "МИРЭА")
        ).containsExactlyInAnyOrderElementsOf(filtered.stream().map(University::toDto).toList());
    }
}
