package server.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateStudentProfileRqDto(

        @NotNull
        UUID id,

        @NotNull
        UUID userId,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotNull
        @Past
        LocalDate birthDate,

        @NotNull
        LocalDate enrollmentDate,

        @NotNull
        UUID classId
) {
}
