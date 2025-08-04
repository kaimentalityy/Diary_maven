package server.integration.dto.response;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SubjectMaterialRespDto(

        @NotNull
        UUID id,

        @NotNull
        String path,

        @NotNull
        UUID subjectId
) {


}
