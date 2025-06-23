package server.business.mapper;

import org.mapstruct.Mapper;
import server.data.entity.Subject;
import server.presentation.dto.request.SubjectRqDto;
import server.presentation.dto.response.SubjectRespDto;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectRespDto toSubjectRespDto(Subject subject);

    Subject toSubject(SubjectRqDto subjectRqDto);
}
