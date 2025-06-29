package server.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.sql.Date;
import java.util.UUID;

public record UpdateAbsenseRqDto(

        @NotNull
        UUID id,

        @NotNull
        UUID lessonId,

        @NotNull
        UUID pupilId,

        @NotNull
        Boolean present,

        @NotNull
        @PastOrPresent
        Date date) {
}
