package ru.mirea.pr_19.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student {
    @Id
    @SequenceGenerator(name = "students_seq", sequenceName = "students_sequence", allocationSize = 1)
    @GeneratedValue(generator = "students_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "university_id")
    @JsonIgnore
    private University university;
}
