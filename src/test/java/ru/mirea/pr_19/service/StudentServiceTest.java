package ru.mirea.pr_19.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.mirea.pr_19.dto.StudentDTO;
import ru.mirea.pr_19.repositories.StudentRepository;
import ru.mirea.pr_19.services.EmailService;
import ru.mirea.pr_19.entities.Student;
import ru.mirea.pr_19.services.impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

public class StudentServiceTest {
    private static StudentRepository studentRepository;
    private static List<Student> entities;

    @BeforeAll
    public static void init() {
        studentRepository = Mockito.mock(StudentRepository.class);

        entities = List.of(
                new Student(0L, "Иван", "Иванов", "Русланович", null),
                new Student(1L, "Петр", "Петров", "Петрович", null),
                new Student(2L, "Сидор", "Сидоров", "Сидорович", null)
        );

        Mockito.when(studentRepository.findAll()).thenReturn(entities);
    }

    @Test
    @DisplayName("Тестирование StudentService#getStudents")
    public void getStudentsShouldReturnStudents() {
        StudentServiceImpl studentService = new StudentServiceImpl(studentRepository);

        Assertions.assertThat(
                studentService.getStudents()
        ).containsAll(entities.stream().map(Student::toDto).toList());
    }

    @Test
    @DisplayName("Тестирование StudentService#getStudentById")
    public void getStudentByIdShouldReturnStudent() {
        Mockito.when(studentRepository.findById(0L)).thenReturn(Optional.ofNullable(entities.getFirst()));

        StudentServiceImpl studentService = new StudentServiceImpl(
                studentRepository
        );

        Assertions.assertThat(
                studentService.getStudentById(0L).getFirstName()
        ).isEqualTo(entities.getFirst().getFirstName());
    }

    @Test
    @DisplayName("Тестирование StudentService#addStudent")
    public void addStudentShouldAddStudent() {
        StudentServiceImpl studentService = new StudentServiceImpl(
                studentRepository
        );

        studentService.addStudent(new StudentDTO(0L, "Иван", "Иванов", "Иванович", null));

        Mockito.verify(studentRepository).save(
                new Student(0L, "Иван", "Иванов", "Иванович", null)
        );
    }

    @Test
    @DisplayName("Тестирование StudentService#deleteStudentById")
    public void deleteStudentByIdShouldDeleteStudent() {
        StudentServiceImpl studentService = new StudentServiceImpl(
                studentRepository
        );

        studentService.deleteStudentById(0L);

        Mockito.verify(studentRepository).deleteById(0L);
    }

    @Test
    @DisplayName("Тестирование StudentService#getFilteredStudents")
    public void getFilteredStudentsShouldReturnFilteredStudents() {
        StudentServiceImpl studentService = new StudentServiceImpl(
                studentRepository
        );

        var filtered = entities.stream().filter(x -> x.getFirstName().equals("Иван")).toList();

        Mockito.when(studentRepository.findStudentsByFirstNameEquals("Иван"))
                .thenReturn(filtered);

        Assertions.assertThat(
                studentService.getFilteredStudents("firstName", "Иван")
        ).containsExactlyInAnyOrderElementsOf(filtered.stream().map(Student::toDto).toList());
    }
}
