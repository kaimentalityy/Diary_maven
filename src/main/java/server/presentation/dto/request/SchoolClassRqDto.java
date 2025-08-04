package server.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SchoolClassRqDto(

        @NotNull
        String letter,

        @NotNull
        Integer number,

        @NotNull
        UUID teacherId,

        @NotNull
        Integer maxCapacity

) {}
