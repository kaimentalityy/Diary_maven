package server.integration.adapter;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import server.integration.dto.response.FileRespDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DiaryStorageService {
    private final RestClient restClient;

    public DiaryStorageService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:9090")
                .build();
    }

    public FileRespDto upload(MultipartFile multipartFile) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        });

        return restClient.post()
                .uri("/api/files")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(FileRespDto.class);
    }


    public void deleteFile(UUID fileId) {
        try {
            restClient.delete()
                    .uri("/api/files/{fileId}", fileId)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            (request, response) -> {
                                throw new RuntimeException(
                                        "Failed to delete file: " + response.getStatusText()
                                );
                            }
                    )
                    .toBodilessEntity();
        } catch (Exception e) {
            throw new RuntimeException("Failed to call storage service: " + e.getMessage(), e);
        }
    }

    public ByteArrayInputStream getFiles(String path) {
        try {
            byte[] fileBytes = restClient.get()
                    .uri(path)
                    .retrieve()
                    .body(byte[].class);

            return new ByteArrayInputStream(fileBytes);
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch file from path: " + path, e);
        }
    }

    public ResponseEntity<Resource> downloadFile(UUID fileId, HttpHeaders requestHeaders) {
        try {
            return restClient.get()
                    .uri("/api/files/{fileId}/download", fileId)
                    .headers(httpHeaders -> httpHeaders.addAll(requestHeaders))
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError(),
                            (request, response) -> {
                                throw new RuntimeException(
                                        "Client error: " + response.getStatusCode() + " - " + response.getStatusText()
                                );
                            }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (request, response) -> {
                                throw new RuntimeException(
                                        "Server error: " + response.getStatusCode() + " - " + response.getStatusText()
                                );
                            }
                    )
                    .toEntity(Resource.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file: " + e.getMessage(), e);
        }
    }

    public List<FileRespDto> findFiles(
            UUID fileId,
            String name,
            Long size,
            String extension,
            LocalDateTime uploadDateFrom,
            LocalDateTime uploadDateTo,
            LocalDateTime lastUpdateFrom,
            LocalDateTime lastUpdateTo,
            String objectName) {

        return restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/api/files");

                    if (fileId != null) uriBuilder.queryParam("fileId", fileId);
                    if (name != null && !name.isEmpty()) uriBuilder.queryParam("name", name);
                    if (size != null) uriBuilder.queryParam("size", size);
                    if (extension != null && !extension.isEmpty()) uriBuilder.queryParam("extension", extension);
                    if (uploadDateFrom != null) uriBuilder.queryParam("uploadDateFrom", uploadDateFrom);
                    if (uploadDateTo != null) uriBuilder.queryParam("uploadDateTo", uploadDateTo);
                    if (lastUpdateFrom != null) uriBuilder.queryParam("lastUpdateFrom", lastUpdateFrom);
                    if (lastUpdateTo != null) uriBuilder.queryParam("lastUpdateTo", lastUpdateTo);
                    if (objectName != null && !objectName.isEmpty()) uriBuilder.queryParam("objectName", objectName);

                    return uriBuilder.build();
                })
                .retrieve()
                .body(new ParameterizedTypeReference<List<FileRespDto>>() {});
    }
}