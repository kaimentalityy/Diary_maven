package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import server.business.facade.MainFacade;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.SchoolClass;
import server.data.entity.WeekSchedule;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.WeekScheduleRespDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WeekScheduleController {

    private final MainFacade facade;

    public ResponseDto<WeekScheduleRespDto> addWeekSchedule(WeekScheduleRqDto weekScheduleRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(weekScheduleRqDto);

        return facade.addLessonWeekSchedule(weekScheduleRqDto);
    }

    public ResponseDto<Void> removeLessonFromSchedule(WeekSchedule weekSchedule) throws SQLException {
        return facade.removeLessonFromSchedule(weekSchedule);
    }

    public List<Lesson> findAllLessonsInADay(DayOfWeek dayOfWeek, SchoolClass schoolClass) throws SQLException {
        return facade.findAllLessonsInADay(dayOfWeek, schoolClass.getId());
    }

    public ResponseDto<WeekSchedule> findLessonById(UUID id) throws SQLException {
        return facade.findWeekScheduleById(id);
    }
}
