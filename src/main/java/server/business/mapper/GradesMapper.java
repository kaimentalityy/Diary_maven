package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import server.data.entity.Grades;
import server.data.entity.Lesson;
import server.data.entity.User;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.request.UpdateGradeRqDto;
import server.presentation.dto.response.GradeRespDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GradesMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "pupilId", source = "pupil.id")
    @Mapping(target = "lessonId", source = "lesson.id")
    GradeRespDto toGradeRespDto(Grades grades);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pupil", source = "pupilId", qualifiedByName = "mapUser")
    @Mapping(target = "lesson", source = "lessonId", qualifiedByName = "mapLesson")
    Grades toGrade(GradeRqDto gradeRqDto);

    @Named("mapUser")
    default User mapUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Named("mapLesson")
    default Lesson mapLesson(UUID lessonId) {
        if (lessonId == null) {
            throw new IllegalArgumentException("Lesson ID cannot be null");
        }
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        return lesson;
    }
}
