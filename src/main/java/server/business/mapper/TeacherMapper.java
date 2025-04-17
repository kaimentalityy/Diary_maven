package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.TeacherOfSubject;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.TeacherRespDto;

import java.util.Optional;

@Component
public class TeacherMapper {

    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

    public TeacherRespDto toTeacherRespDto(TeacherOfSubject teacherOfSubject) {
        return new TeacherRespDto(teacherOfSubject.getId(), teacherOfSubject.getSubjectId(), teacherOfSubject.getTeacherId());
    }

    public TeacherOfSubject toTeacher(TeacherRqDto teacherRqDto) {
        TeacherOfSubject teacherOfSubject = new TeacherOfSubject();
        teacherOfSubject.setSubjectId(teacherRqDto.subjectId());
        teacherOfSubject.setTeacherId(teacherRqDto.teacherId());
        return teacherOfSubject;
    }

}
