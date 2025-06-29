package server.presentation.dto.response;

import java.util.UUID;

public record CheckAttendanceRespDto (UUID id, Boolean present) {
}
