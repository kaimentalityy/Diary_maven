package server.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import server.data.enums.Subject;

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

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Subject subject;
}
