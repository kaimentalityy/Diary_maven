package server.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.business.service.SubjectMaterialService;
import server.integration.adapter.DiaryStorageService;
import server.integration.dto.request.FileSearchRqDto;
import server.integration.dto.request.SubjectMaterialRqDto;
import server.integration.dto.response.FileRespDto;
import server.integration.dto.response.SubjectMaterialRespDto;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/diary")
@Tag(name = "Diary Storage", description = "APIs for managing subject materials (upload, download, delete)")
public class DiaryStorageController {

    private final SubjectMaterialService subjectMaterialService;
    private final DiaryStorageService diaryStorageService;

    @Operation(
            summary = "Upload a subject material file",
            description = "Uploads a file to storage and associates it with a subject in the database.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "File uploaded successfully",
                            content = @Content(schema = @Schema(implementation = SubjectMaterialRespDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid file format or missing subject ID",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectMaterialRespDto uploadMaterial(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The file and subject data to be uploaded",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = SubjectMaterialRqDto.class))
            )
            SubjectMaterialRqDto subjectMaterialRqDto
    ) {
        return subjectMaterialService.createMaterial(subjectMaterialRqDto);
    }

    @Operation(
            summary = "Delete a subject material",
            description = "Deletes both the database record and the file from storage.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "File deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "File not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @DeleteMapping("/{materialId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterial(
            @Parameter(description = "UUID of the material to delete", required = true)
            @PathVariable UUID materialId) {
        subjectMaterialService.deleteMaterial(materialId);
    }

    @Operation(
            summary = "Download a subject material file",
            description = "Downloads a file by its ID. Supports `Range` headers for partial downloads (if supported by the file storage).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "File downloaded successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    ),
                    @ApiResponse(responseCode = "404", description = "File not found", content = @Content),
                    @ApiResponse(responseCode = "416", description = "Requested range not satisfiable", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(
            @Parameter(description = "UUID of the file to download", required = true)
            @PathVariable UUID fileId,

            @Parameter(description = "Optional HTTP headers like Range for partial download")
            @RequestHeader HttpHeaders headers) {

        return diaryStorageService.downloadFile(fileId, headers);
    }

    @Operation(
            summary = "Search files",
            description = "Search for uploaded files using multiple optional filter criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of matching files")
            }
    )
    @GetMapping("/files")
    public List<FileRespDto> searchFiles(
            @ModelAttribute FileSearchRqDto searchParams
    ) {
        return diaryStorageService.findFiles(
                searchParams.getFileId(),
                searchParams.getName(),
                searchParams.getSize(),
                searchParams.getExtension(),
                searchParams.getUploadDateFrom(),
                searchParams.getUploadDateTo(),
                searchParams.getLastUpdateFrom(),
                searchParams.getLastUpdateTo(),
                searchParams.getObjectName()
        );
    }

}
