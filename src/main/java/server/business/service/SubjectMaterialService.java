package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.SubjectMaterialMapper;
import server.data.entity.Subject;
import server.data.entity.SubjectMaterial;
import server.data.repository.SubjectMaterialRepository;
import server.integration.adapter.DiaryStorageService;
import server.integration.dto.request.SubjectMaterialRqDto;
import server.integration.dto.response.FileRespDto;
import server.integration.dto.response.SubjectMaterialRespDto;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectMaterialService {

    private final SubjectMaterialRepository subjectMaterialRepository;
    private final DiaryStorageService diaryStorageService;
    private final SubjectService subjectService;
    private final SubjectMaterialMapper subjectMaterialMapper;

    public SubjectMaterialRespDto createMaterial(SubjectMaterialRqDto requestDto) {
        FileRespDto fileRespDto;
        try {
            fileRespDto = diaryStorageService.upload(requestDto.file());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Subject subject = subjectService.findById(requestDto.subjectId());

        String path = String.format("classes/%s/subjects/%s/materials/%s/",
                subject.getSchoolClass().getId(),
                subject.getId(),
                fileRespDto.fileId());

        SubjectMaterial material = subjectMaterialMapper.toEntity(subject, path);
        material = subjectMaterialRepository.save(material);

        return subjectMaterialMapper.toDto(material);
    }

    public void deleteMaterial(UUID materialId) {
        SubjectMaterial material = subjectMaterialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));

        String fileId = extractFileIdFromPath(material.getPath());

        diaryStorageService.deleteFile(UUID.fromString(fileId));
        subjectMaterialRepository.delete(material);
    }

    private String extractFileIdFromPath(String path) {
        String[] parts = path.split("/");

        for (int i = 0; i < parts.length; i++) {
            if ("materials".equals(parts[i]) && i + 1 < parts.length) {
                return parts[i + 1];
            }
        }
        throw new IllegalArgumentException("Invalid path format");
    }

}
