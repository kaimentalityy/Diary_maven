package server.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AbsenseRqDto(

                @NotNull UUID lessonId,

                @NotNull UUID pupilId,

                @NotNull Boolean present

) {
}
