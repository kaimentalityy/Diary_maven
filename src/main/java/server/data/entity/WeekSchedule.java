package server.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class WeekSchedule {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID lessonId;
    private Integer weekDayId;
    private Integer lessonNumber;

}
