package server.presentation.dto.response;

import java.util.UUID;

public record WeekScheduleRespDto (UUID id, Integer day_of_week, UUID lesson_id, Integer lesson_number) {
}

