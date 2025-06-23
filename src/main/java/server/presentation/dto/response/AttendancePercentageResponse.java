package server.presentation.dto.response;

import java.util.UUID;

public record AttendancePercentageResponse(UUID userId, UUID classId, Double percentage) {}
