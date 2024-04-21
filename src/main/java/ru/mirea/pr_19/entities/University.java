package ru.mirea.pr_19.entities;

import jakarta.persistence.*;
import lombok.*;

import ru.mirea.pr_19.dto.UniversityDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "universities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class University {
    @Id
    @SequenceGenerator(name = "universities_seq", sequenceName = "universities_sequence", allocationSize = 1)
    @GeneratedValue(generator = "universities_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private String creationDate;

    @OneToMany(mappedBy = "university")
    private List<Student> students = new ArrayList<>();

    public UniversityDTO toDto() {
        return new UniversityDTO(id, name, creationDate, students.stream().map(Student::toDto).toList());
    }
}
