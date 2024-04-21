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
    private static EmailService emailService;
    private static StudentRepository studentRepository;
    private static List<Student> entities;

    @BeforeAll
    public static void init() {
        emailService = Mockito.mock(EmailService.class);
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
        StudentServiceImpl studentService = new StudentServiceImpl(studentRepository, emailService);

        Assertions.assertThat(
                studentService.getStudents()
        ).containsAll(entities.stream().map(Student::toDto).toList());
    }

    @Test
    @DisplayName("Тестирование StudentService#getStudentById")
    public void getStudentByIdShouldReturnStudent() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(entities.get(1)));

        StudentServiceImpl studentService = new StudentServiceImpl(
                studentRepository,
                emailService
        );

        Assertions.assertThat(
                studentService.getStudentById(1L).getFirstName()
        ).isEqualTo(entities.get(1).getFirstName());
    }

    @Test
    @DisplayName("Тестирование StudentService#addStudent")
    public void addStudentShouldAddStudent() {
        StudentServiceImpl studentService = new StudentServiceImpl(
                studentRepository,
                emailService
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
                studentRepository,
                emailService
        );

        studentService.deleteStudentById(0L);

        Mockito.verify(studentRepository).deleteById(0L);
    }

    @Test
    @DisplayName("Тестирование StudentService#getFilteredStudents")
    public void getFilteredStudentsShouldReturnFilteredStudents() {
        StudentServiceImpl studentService = new StudentServiceImpl(
                studentRepository,
                emailService
        );

        var filtered = entities.stream().filter(x -> x.getFirstName().equals("Иван")).toList();

        Mockito.when(studentRepository.findStudentsByFirstNameEquals("Иван"))
                .thenReturn(filtered);

        Assertions.assertThat(
                studentService.getFilteredStudents("firstName", "Иван")
        ).containsExactlyInAnyOrderElementsOf(filtered.stream().map(Student::toDto).toList());
    }
}
