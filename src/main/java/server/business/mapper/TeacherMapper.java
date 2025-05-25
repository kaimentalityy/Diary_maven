package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.TeacherOfSubject;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.request.UpdateTeacherRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.TeacherRespDto;

import java.util.Optional;

@Component
public class TeacherMapper {

    public TeacherRespDto toTeacherRespDto(TeacherOfSubject teacherOfSubject) {
        return new TeacherRespDto(teacherOfSubject.getId(), teacherOfSubject.getSubjectId(), teacherOfSubject.getTeacherId());
    }

    public TeacherOfSubject toTeacher(TeacherRqDto teacherRqDto) {
        TeacherOfSubject teacherOfSubject = new TeacherOfSubject();
        teacherOfSubject.setSubjectId(teacherRqDto.subjectId());
        teacherOfSubject.setTeacherId(teacherRqDto.teacherId());
        return teacherOfSubject;
    }

    public TeacherOfSubject toTeacherForUpdate(UpdateTeacherRqDto updateTeacherRqDto) {
        TeacherOfSubject teacherOfSubject = new TeacherOfSubject();
        teacherOfSubject.setId(updateTeacherRqDto.id());
        teacherOfSubject.setSubjectId(updateTeacherRqDto.subjectId());
        teacherOfSubject.setTeacherId(updateTeacherRqDto.teacherId());
        return teacherOfSubject;
    }

}
