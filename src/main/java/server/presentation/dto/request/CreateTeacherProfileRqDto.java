package server.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.UUID;

public record CreateTeacherProfileRqDto(

        @NotNull UUID userId,

        @NotBlank String firstName,

        @NotBlank String lastName,

        @NotNull @Past LocalDate hireDate

) {
}
