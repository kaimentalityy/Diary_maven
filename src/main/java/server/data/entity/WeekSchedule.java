package server.data.entity;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class WeekSchedule {

    private UUID lesson_id;
    private UUID id;
    private Integer week_day_id;
    private Integer lesson_number;
}
