package server.presentation.dto.request;

import java.util.UUID;

public record WeekScheduleRqDto (Integer day_of_week, UUID lesson_id, Integer lesson_number) {
}
