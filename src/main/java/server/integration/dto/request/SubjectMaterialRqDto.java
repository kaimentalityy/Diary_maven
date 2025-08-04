package server.integration.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record SubjectMaterialRqDto(
        @Schema(
                description = "File to upload",
                requiredMode = Schema.RequiredMode.REQUIRED,
                type = "string",
                format = "binary"
        )
        @NotNull
        MultipartFile file,

        @Schema(
                description = "ID of the subject to associate with the file",
                example = "bb58ec50-1e1f-4f31-9236-d6c4926facbe",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        UUID subjectId
) {}