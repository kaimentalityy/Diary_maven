package server.data.entity;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class WeekSchedule {

    private UUID lessonId;
    private UUID id;
    private Integer weekDayId;
    private Integer lessonNumber;

}
