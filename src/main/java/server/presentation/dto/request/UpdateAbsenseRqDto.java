package server.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateAbsenseRqDto(

                @NotNull UUID id,

                @NotNull UUID lessonId,

                @NotNull UUID pupilId,

                @NotNull Boolean present

) {
}
