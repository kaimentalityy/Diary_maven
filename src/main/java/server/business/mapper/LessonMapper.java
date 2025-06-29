package server.business.mapper;

import org.mapstruct.*;
import server.data.entity.*;
import server.presentation.dto.request.LessonRqDto;
import server.presentation.dto.response.LessonRespDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "class_id", source = "schoolClass.id")
    @Mapping(target = "teacherOfSubjectId", source = "teacherOfSubject.id")
    @Mapping(target = "subject_id", source = "subject.id")
    LessonRespDto toLessonRespDto(Lesson lesson);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schoolClass", source = "class_id", qualifiedByName = "mapSchoolClass")
    @Mapping(target = "teacherOfSubject", source = "teacherOfSubjectId", qualifiedByName = "mapTeacherOfSubject")
    @Mapping(target = "subject", source = "subject_id", qualifiedByName = "mapSubject")
    Lesson toLesson(LessonRqDto lessonRqDto);

    @Named("mapSchoolClass")
    default SchoolClass mapSchoolClass(UUID classId) {
        if (classId == null) {
            throw new IllegalArgumentException("Class ID cannot be null");
        }
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(classId);
        return schoolClass;
    }

    @Named("mapTeacherOfSubject")
    default TeacherOfSubject mapTeacherOfSubject(UUID teacherOfSubjectId) {
        if (teacherOfSubjectId == null) {
            throw new IllegalArgumentException("TeacherOfSubject ID cannot be null");
        }
        TeacherOfSubject teacherOfSubject = new TeacherOfSubject();
        teacherOfSubject.setId(teacherOfSubjectId);
        return teacherOfSubject;
    }

    @Named("mapSubject")
    default Subject mapSubject(UUID subjectId) {
        if (subjectId == null) {
            throw new IllegalArgumentException("Subject ID cannot be null");
        }
        Subject subject = new Subject();
        subject.setId(subjectId);
        return subject;
    }
}