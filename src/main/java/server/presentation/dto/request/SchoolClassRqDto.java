package server.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SchoolClassRqDto(

        @NotBlank
        String letter,

        @NotBlank
        String number,

        @NotNull
        UUID teacherId,

        @NotNull
        Integer maxCapacity

) {}
