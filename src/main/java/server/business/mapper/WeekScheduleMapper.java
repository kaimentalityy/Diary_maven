package server.business.mapper;

import org.mapstruct.*;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.WeekScheduleRespDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface WeekScheduleMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "dayOfWeek", source = "dayOfWeek.id")
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "lessonNumber", source = "lessonNumber")
    WeekScheduleRespDto toWeekScheduleRespDto(WeekSchedule weekSchedule);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dayOfWeek", source = "dayOfWeek", qualifiedByName = "mapDayOfWeek")
    @Mapping(target = "lesson", source = "lessonId", qualifiedByName = "mapLesson")
    @Mapping(target = "lessonNumber", source = "lessonNumber")
    WeekSchedule toWeekSchedule(WeekScheduleRqDto weekScheduleRqDto);

    @Named("mapDayOfWeek")
    default DayOfWeek mapDayOfWeek(Integer dayOfWeekId) {
        if (dayOfWeekId == null) {
            throw new IllegalArgumentException("Day of week ID cannot be null");
        }
        DayOfWeek dayOfWeek = new DayOfWeek();
        dayOfWeek.setId(dayOfWeekId);
        return dayOfWeek;
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