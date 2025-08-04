package server.integration.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileRespDto(
        UUID fileId,
        String name,
        String path,
        long size,
        String contentType,
        String extension,
        LocalDateTime uploadTimestamp

) {}
