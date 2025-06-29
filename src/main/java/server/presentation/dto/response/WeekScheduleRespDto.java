package server.presentation.dto.response;

import java.util.UUID;

public record WeekScheduleRespDto (UUID id, Integer dayOfWeek, UUID lessonId, Integer lessonNumber) {
}

