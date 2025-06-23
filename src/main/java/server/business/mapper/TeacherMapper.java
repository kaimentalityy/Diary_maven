package server.business.mapper;

import org.mapstruct.Mapper;
import server.data.entity.TeacherOfSubject;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.request.UpdateTeacherRqDto;
import server.presentation.dto.response.TeacherRespDto;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherRespDto toTeacherRespDto(TeacherOfSubject teacherOfSubject);

    TeacherOfSubject toTeacher(TeacherRqDto teacherRqDto);

    TeacherOfSubject toTeacherForUpdate(UpdateTeacherRqDto updateTeacherRqDto);

}
