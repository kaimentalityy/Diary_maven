package server.presentation.dto.response;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SubjectRespDto(

        @NotNull
        UUID id,

        @NotNull
        String name,

        @NotNull
        UUID classId

) {}
