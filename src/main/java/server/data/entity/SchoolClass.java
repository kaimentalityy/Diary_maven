package server.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "schoolclass")
public class SchoolClass {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column
    private String letter;

    @Column
    private String number;

    @OneToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @Column
    private Integer maxCapacity;
}