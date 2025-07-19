package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import server.data.entity.SchoolClass;
import server.data.entity.Subject;
import server.presentation.dto.request.SubjectRqDto;
import server.presentation.dto.response.SubjectRespDto;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "classId", source = "schoolClass.id")
    SubjectRespDto toSubjectRespDto(Subject subject);

    @Mapping(target = "id", ignore = true)
    Subject toSubject(SubjectRqDto subjectRqDto, SchoolClass schoolClass);
}