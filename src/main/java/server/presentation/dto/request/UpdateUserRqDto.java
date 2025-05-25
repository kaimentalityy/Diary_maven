package server.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateUserRqDto(

        @NotNull
        UUID id,

        @NotBlank
        @Size(min = 5, max = 20)
        String login,

        @NotBlank
        @Size(min = 5, max = 20)
        String password,

        @NotBlank
        String name,

        @NotBlank
        String lastname,

        @NotNull
        UUID roleId,

        UUID classId,

        @NotNull
        Boolean isBlocked,

        @NotNull
        @Min(5)
        Integer age

) {}



