package server.business.mapper;

import server.data.entity.WeekSchedule;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.WeekScheduleRespDto;

import java.util.Optional;

public class WeekScheduleMapper {

    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

    public WeekScheduleRespDto toWeekScheduleRespDto(WeekSchedule weekSchedule) {
        return new WeekScheduleRespDto(weekSchedule.getId(), weekSchedule.getWeek_day_id(), weekSchedule.getLesson_id(), weekSchedule.getLesson_number());
    }

    public WeekSchedule toWeekSchedule(WeekScheduleRqDto weekScheduleRqDto) {
        WeekSchedule weekSchedule = new WeekSchedule();
        weekSchedule.setWeek_day_id(weekScheduleRqDto.day_of_week());
        weekSchedule.setLesson_id(weekScheduleRqDto.lesson_id());
        weekSchedule.setLesson_number(weekScheduleRqDto.lesson_number());
        return weekSchedule;
    }
}
