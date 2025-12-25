package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import server.data.entity.Grade;
import server.data.entity.Lesson;
import server.data.entity.User;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.response.GradeRespDto;

@Mapper(componentModel = "spring")
public interface GradesMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "pupilId", source = "pupil.id")
    @Mapping(target = "lessonId", source = "lesson.id")
    GradeRespDto toGradeRespDto(Grade grade);

    @Mapping(target = "id", ignore = true)
    Grade toGrade(GradeRqDto gradeRqDto, User pupil, Lesson lesson);

}
