package server.integration.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileRespDto(
        UUID fileId,
        String name,
        long size,
        String contentType,
        String extension,
        LocalDateTime uploadTimestamp,
        LocalDateTime lastUpdate,
        LocalDateTime deletedAt,
        String status
) {
}
