package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.WeekSchedule;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.WeekScheduleRespDto;

import java.util.Optional;

@Component
public class WeekScheduleMapper {

    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

    public WeekScheduleRespDto toWeekScheduleRespDto(WeekSchedule weekSchedule) {
        return new WeekScheduleRespDto(weekSchedule.getId(), weekSchedule.getWeekDayId(), weekSchedule.getLessonId(), weekSchedule.getLessonNumber());
    }

    public WeekSchedule toWeekSchedule(WeekScheduleRqDto weekScheduleRqDto) {
        WeekSchedule weekSchedule = new WeekSchedule();
        weekSchedule.setWeekDayId(weekScheduleRqDto.day_of_week());
        weekSchedule.setLessonId(weekScheduleRqDto.lesson_id());
        weekSchedule.setLessonNumber(weekScheduleRqDto.lesson_number());
        return weekSchedule;
    }
}
