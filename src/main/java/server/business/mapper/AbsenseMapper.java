package server.business.mapper;

import org.mapstruct.*;
import server.data.entity.Attendance;
import server.data.entity.Lesson;
import server.data.entity.User;
import server.presentation.dto.request.AbsenseRqDto;
import server.presentation.dto.request.AttendancePercentageRequest;
import server.presentation.dto.request.UpdateAbsenseRqDto;
import server.presentation.dto.response.AbsenseRespDto;
import server.presentation.dto.response.AttendancePercentageResponse;
import server.presentation.dto.response.CheckAttendanceRespDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AbsenseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "pupilId", source = "pupil.id")
    @Mapping(target = "present", source = "present")
    @Mapping(target = "date", source = "date")
    AbsenseRespDto toAttendanceRespDto(Attendance attendance);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "present", source = "present")
    CheckAttendanceRespDto toCheckAttendanceRespDto(Attendance attendance);

    @Mapping(target = "userId", source = "request.userId")
    @Mapping(target = "classId", source = "request.classId")
    @Mapping(target = "percentage", source = "percentage")
    AttendancePercentageResponse toAttendancePercentageResponse(AttendancePercentageRequest request, double percentage);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", source = "lessonId", qualifiedByName = "mapLesson")
    @Mapping(target = "pupil", source = "pupilId", qualifiedByName = "mapUser")
    @Mapping(target = "present", source = "present")
    @Mapping(target = "date", source = "date")
    Attendance toAttendance(AbsenseRqDto absenseRqDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "lesson", source = "lessonId", qualifiedByName = "mapLesson")
    @Mapping(target = "pupil", source = "pupilId", qualifiedByName = "mapUser")
    @Mapping(target = "present", source = "present")
    @Mapping(target = "date", source = "date")
    Attendance toAttendanceForUpdate(UpdateAbsenseRqDto updateAbsenseRqDto);

    @Named("mapLesson")
    default Lesson mapLesson(UUID lessonId) {
        if (lessonId == null) {
            throw new IllegalArgumentException("Lesson ID cannot be null");
        }
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        return lesson;
    }

    @Named("mapUser")
    default User mapUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = new User();
        user.setId(userId);
        return user;
    }
}