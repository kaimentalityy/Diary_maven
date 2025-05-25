package server.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record WeekScheduleRqDto(

        @NotNull
        @Min(1)
        @Max(7)
        Integer day_of_week,

        @NotNull
        UUID lesson_id,

        @NotNull
        @Min(1)
        @Max(10)
        Integer lesson_number

) {}

