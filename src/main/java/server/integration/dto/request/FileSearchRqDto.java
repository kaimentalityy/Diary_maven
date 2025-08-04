package server.integration.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Search criteria for filtering files")
public class FileSearchRqDto {

    @Schema(description = "File ID")
    private UUID fileId;

    @Schema(description = "File name (partial match, case-insensitive)")
    private String name;

    @Schema(description = "Minimum file size in bytes")
    private Long size;

    @Schema(description = "File extension")
    private String extension;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Filter by upload date from (ISO format)", example = "2025-08-01T00:00:00")
    private LocalDateTime uploadDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Filter by upload date to (ISO format)", example = "2025-08-04T23:59:59")
    private LocalDateTime uploadDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Filter by last update from (ISO format)")
    private LocalDateTime lastUpdateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Filter by last update to (ISO format)")
    private LocalDateTime lastUpdateTo;

    @Schema(description = "Object name associated with the file")
    private String objectName;
}
