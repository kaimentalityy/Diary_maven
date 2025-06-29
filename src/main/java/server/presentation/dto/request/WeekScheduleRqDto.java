package server.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record WeekScheduleRqDto(

        @NotNull
        @Min(1)
        @Max(7)
        Integer dayOfWeek,

        @NotNull
        UUID lessonId,

        @NotNull
        @Min(1)
        @Max(10)
        Integer lessonNumber

) {}

