package server.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import server.data.enums.DayOfWeek;

import java.util.UUID;

@Data
@Entity
@Table(name = "weekschedule")
public class WeekSchedule {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column
    private Integer lessonNumber;

}
