package server.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignPupilToClassRqDto (

        @NotNull
        UUID pupilId,

        @NotNull
        UUID classId

) {}
