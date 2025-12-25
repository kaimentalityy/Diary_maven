package server.business.mapper;

import org.mapstruct.*;
import server.data.entity.*;
import server.presentation.dto.request.LessonRqDto;
import server.presentation.dto.response.LessonRespDto;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "classId", source = "schoolClass.id")
    @Mapping(target = "teacherOfSubjectId", source = "teacherOfSubject.id")
    @Mapping(target = "subjectId", source = "subject.id")
    LessonRespDto toLessonRespDto(Lesson lesson);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacherAssignment", source = "teacherAssignment")
    @Mapping(target = "lessonDate", source = "lessonRqDto.localDate")
    Lesson toLesson(LessonRqDto lessonRqDto, SchoolClass schoolClass, Subject subject,
            TeacherAssignment teacherAssignment);
}