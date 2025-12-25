package server.business.mapper;

import org.mapstruct.*;
import server.data.entity.Subject;
import server.data.entity.TeacherAssignment;
import server.data.entity.User;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.request.UpdateTeacherRqDto;
import server.presentation.dto.response.TeacherRespDto;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "teacherId", source = "teacher.id")
    TeacherRespDto toTeacherRespDto(TeacherAssignment teacherAssignment);

    @Mapping(target = "id", ignore = true)
    TeacherAssignment toTeacherOfSubject(TeacherRqDto teacherRqDto, User teacher, Subject subject);

    @Mapping(target = "id",  ignore = true)
    TeacherAssignment toTeacherForUpdate(UpdateTeacherRqDto updateTeacherRqDto, Subject subject, User teacher);

}