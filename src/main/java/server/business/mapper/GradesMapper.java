package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.Grades;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.GradeRespDto;

import java.util.Optional;

@Component
public class GradesMapper {

    public GradeRespDto toGradeRespDto(Grades grades) {
        return new GradeRespDto(grades.getId(), grades.getPupilId(), grades.getLessonId(), grades.getGrade());
    }

    public Grades toGrade(GradeRqDto gradeRqDto) {
        Grades grades = new Grades();
        grades.setPupilId(gradeRqDto.pupil_id());
        grades.setLessonId(gradeRqDto.lesson_id());
        grades.setGrade(gradeRqDto.grade());
        return grades;
    }
}
