package server.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GradeRqDto(

        @NotNull
        UUID pupil_id,

        @NotNull
        UUID lesson_id,

        @NotBlank
        String grade

) {}

