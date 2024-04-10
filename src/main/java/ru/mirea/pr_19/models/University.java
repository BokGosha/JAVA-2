package ru.mirea.pr_19.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "universities")
@Getter
@Setter
public class University {
    @Id
    @SequenceGenerator(name = "universities_seq", sequenceName = "universities_sequence", allocationSize = 1)
    @GeneratedValue(generator = "universities_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "university")
    private List<Student> students = new ArrayList<>();
}
