package server.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SubjectRqDto (

        @NotNull
        String name,

        @NotNull
        UUID class_id
){}
