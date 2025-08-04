package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import server.data.entity.Subject;
import server.data.entity.SubjectMaterial;
import server.integration.dto.response.SubjectMaterialRespDto;

@Mapper(componentModel = "spring")
public interface SubjectMaterialMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "path", source = "path")
    SubjectMaterial toEntity(Subject subject, String path);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "subjectId", source = "subject.id")
    SubjectMaterialRespDto toDto(SubjectMaterial entity);
}
