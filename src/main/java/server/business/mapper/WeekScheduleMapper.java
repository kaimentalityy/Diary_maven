package server.business.mapper;

import org.mapstruct.Mapper;
import server.data.entity.WeekSchedule;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.WeekScheduleRespDto;

@Mapper(componentModel = "spring")
public interface WeekScheduleMapper {

    WeekScheduleRespDto toWeekScheduleRespDto(WeekSchedule weekSchedule);

    WeekSchedule toWeekSchedule(WeekScheduleRqDto weekScheduleRqDto);
}
