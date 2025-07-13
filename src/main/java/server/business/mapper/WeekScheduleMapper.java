package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import server.data.enums.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.WeekScheduleRespDto;

@Mapper(componentModel = "spring")
public interface WeekScheduleMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "dayOfWeek", source = "dayOfWeek.id")
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "lessonNumber", source = "lessonNumber")
    WeekScheduleRespDto toWeekScheduleRespDto(WeekSchedule weekSchedule);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    WeekSchedule toWeekSchedule(WeekScheduleRqDto weekScheduleRqDto, DayOfWeek dayOfWeek,  Lesson lesson);
}