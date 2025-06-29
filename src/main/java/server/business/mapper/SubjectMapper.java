package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import server.data.entity.Subject;
import server.presentation.dto.request.SubjectRqDto;
import server.presentation.dto.response.SubjectRespDto;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SubjectRespDto toSubjectRespDto(Subject subject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Subject toSubject(SubjectRqDto subjectRqDto);
}
