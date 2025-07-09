package server.business.mapper;

import org.mapstruct.*;
import server.data.entity.Attendance;
import server.data.entity.Lesson;
import server.data.entity.SchoolClass;
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
    AbsenseRespDto toAttendanceRespDto(Attendance attendance);

    //@Mapping(target = "id", source = "id")
    //CheckAttendanceRespDto toCheckAttendanceRespDto(Attendance attendance);

    //@Mapping(target = "userId", source = "request.userId")
    //@Mapping(target = "classId", source = "request.classId")
    //AttendancePercentageResponse toAttendancePercentageResponse(AttendancePercentageRequest request, double percentage);

    @Mapping(target = "id", ignore = true)
    Attendance toAttendance(AbsenseRqDto absenseRqDto, Lesson lesson, User user);

    //@Mapping(target = "id", source = "id")
    //@Mapping(target = "lesson", source = "lessonId", qualifiedByName = "mapLesson")
    //@Mapping(target = "pupil", source = "pupilId", qualifiedByName = "mapUser")
    //Attendance toAttendanceForUpdate(UpdateAbsenseRqDto updateAbsenseRqDto);
}