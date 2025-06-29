package server.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateGradeRqDto(

        @NotNull
        UUID id,

        @NotNull
        String column,

        @NotNull
        String value
) {}
