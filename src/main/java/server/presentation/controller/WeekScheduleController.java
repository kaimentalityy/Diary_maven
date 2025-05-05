package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.WeekScheduleRespDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weekSchedules")
public class WeekScheduleController {

    private final MainFacade facade;

    @PostMapping
    public ResponseDto<WeekScheduleRespDto> addWeekSchedule(@RequestBody WeekScheduleRqDto weekScheduleRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(weekScheduleRqDto);

        return facade.addLessonWeekSchedule(weekScheduleRqDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> removeLessonFromSchedule(@PathVariable UUID id) throws SQLException {

        ResponseDto<WeekSchedule> weekSchedule = facade.findWeekScheduleById(id);

        return facade.removeLessonFromSchedule(weekSchedule.getResult().orElse(null));
    }

    @GetMapping
    public List<Lesson> findAllLessonsInADay(@RequestParam int dayOfWeekId, @RequestParam UUID schoolClassId) throws SQLException {
        return facade.findAllLessonsInADay(facade.findDayOfWeekById(dayOfWeekId).getResult().orElse(null), schoolClassId);
    }

    @GetMapping("/{id}")
    public ResponseDto<WeekSchedule> findLessonById(@PathVariable UUID id) throws SQLException {
        return facade.findWeekScheduleById(id);
    }

    @GetMapping("/testSimple")
    public String test() {
        return "It works!";
    }

}
