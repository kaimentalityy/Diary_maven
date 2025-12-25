package server.business.mapper;

import org.mapstruct.*;
import server.data.entity.Attendance;
import server.data.entity.Lesson;
import server.data.entity.SchoolClass;
import server.data.entity.StudentProfile;
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

    @Mapping(target = "id", source = "id")
    CheckAttendanceRespDto toCheckAttendanceRespDto(Attendance attendance);

    @Mapping(target = "userId", source = "request.userId")
    @Mapping(target = "classId", source = "request.classId")
    AttendancePercentageResponse toAttendancePercentageResponse(AttendancePercentageRequest request, double percentage);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pupil", source = "pupil")
    @Mapping(target = "lesson", source = "lesson")
    @Mapping(target = "present", source = "absenseRqDto.present")
    Attendance toAttendance(AbsenseRqDto absenseRqDto, Lesson lesson, StudentProfile pupil);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "present", source = "updateAbsenseRqDto.present")
    @Mapping(target = "pupil", source = "pupil")
    @Mapping(target = "lesson", source = "lesson")
    Attendance toAttendanceForUpdate(UpdateAbsenseRqDto updateAbsenseRqDto, Lesson lesson, StudentProfile pupil);
}