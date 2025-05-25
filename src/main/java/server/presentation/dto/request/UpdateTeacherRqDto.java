package server.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateTeacherRqDto(

        @NotNull
        UUID id,

        @NotNull
        UUID subjectId,

        @NotNull
        UUID teacherId

) {}