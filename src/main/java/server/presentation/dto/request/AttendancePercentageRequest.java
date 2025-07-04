package server.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AttendancePercentageRequest(
        @NotNull
        UUID userId,

        @NotNull
        UUID classId
) {}
