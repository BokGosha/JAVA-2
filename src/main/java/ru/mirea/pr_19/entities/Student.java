package ru.mirea.pr_19.entities;

import jakarta.persistence.*;
import lombok.*;

import ru.mirea.pr_19.dto.StudentDTO;

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
    private University university;

    public void setUniversity(University university) {
        if (this.university != null) {
            this.university.getStudents().remove(this);
        }

        university.getStudents().add(this);

        this.university = university;
    }

    public void removeUniversity() {
        if (this.university != null) {
            this.university.getStudents().remove(this);
        }

        this.university = null;
    }

    public StudentDTO toDto() {
        return new StudentDTO(id, firstName, middleName, lastName, university == null ? null : university.getId());
    }
}
