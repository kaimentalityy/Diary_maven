package server.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GradeRqDto(

        @NotNull
        UUID pupilId,

        @NotNull
        UUID lessonId,

        @NotBlank
        String grade

) {}

