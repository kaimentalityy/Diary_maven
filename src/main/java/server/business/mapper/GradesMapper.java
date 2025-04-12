package server.business.mapper;

import server.data.entity.Grades;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.GradeRespDto;
import server.presentation.dto.response.ResponseDto;

import java.util.Optional;

public class GradesMapper {

    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

    public GradeRespDto toGradeRespDto(Grades grades) {
        return new GradeRespDto(grades.getId(), grades.getPupil_id(), grades.getLesson_id(), grades.getGrade());
    }

    public Grades toGrade(GradeRqDto gradeRqDto) {
        Grades grades = new Grades();
        grades.setPupil_id(gradeRqDto.pupil_id());
        grades.setLesson_id(gradeRqDto.lesson_id());
        grades.setGrade(gradeRqDto.grade());
        return grades;
    }
}
