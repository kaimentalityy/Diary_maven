package server.presentation.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record LessonRqDto(

        @NotNull
        @NotBlank
        UUID class_id,

        @NotNull
        @NotBlank
        UUID teacher_of_subject_id,

        @NotNull
        @FutureOrPresent
        LocalDateTime date,

        @NotNull
        @NotBlank
        UUID subject_id

) {}
