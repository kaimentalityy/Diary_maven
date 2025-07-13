package server.business.mapper;

import org.mapstruct.*;
import server.data.enums.Subject;
import server.data.entity.TeacherOfSubject;
import server.data.entity.User;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.request.UpdateTeacherRqDto;
import server.presentation.dto.response.TeacherRespDto;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "teacherId", source = "teacher.id")
    TeacherRespDto toTeacherRespDto(TeacherOfSubject teacherOfSubject);

    @Mapping(target = "id", ignore = true)
    TeacherOfSubject toTeacherOfSubject(TeacherRqDto teacherRqDto, User teacher, Subject subject);

    @Mapping(target = "id",  ignore = true)
    TeacherOfSubject toTeacherForUpdate(UpdateTeacherRqDto updateTeacherRqDto, Subject subject, User teacher);

}