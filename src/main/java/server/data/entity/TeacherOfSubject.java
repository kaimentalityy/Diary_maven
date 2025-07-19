package server.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "teacherofsubject")
public class TeacherOfSubject {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "teacher_id",  nullable = false)
    private User teacher;

    @OneToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
}
