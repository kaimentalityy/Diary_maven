package server.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubjectRqDto(

        @Size
        @NotBlank
        String name
) {
}
