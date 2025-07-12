package server.presentation.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record LessonRqDto(

        @NotNull
        UUID classId,

        @NotNull
        UUID teacherOfSubjectId,

        @NotNull
        @FutureOrPresent
        LocalDateTime localDate,

        @NotNull
        UUID subjectId

) {}
