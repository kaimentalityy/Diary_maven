package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import server.business.facade.MainFacade;
import server.data.entity.Lesson;
import server.presentation.dto.request.LessonRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.LessonRespDto;
import server.presentation.dto.response.ResponseDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LessonController {
    private final MainFacade facade;

    public ResponseDto<LessonRespDto> createLesson(LessonRqDto lessonRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(lessonRqDto);

        return facade.assignLesson(lessonRqDto);
    }

    public ResponseDto<Void> deleteLesson(Lesson lesson) throws SQLException, ConstraintViolationException {
        if (findLessonById(lesson.getId()).getResult().isPresent()) {
            Validator.notNull(lesson);
            return facade.removeLesson(lesson);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Lesson not found"));
    }

    public List<Lesson> findAllLessonsByDate(LocalDateTime localDateTime) throws SQLException {
        return facade.findAllLessonsByDate(localDateTime);
    }

    public ResponseDto<Lesson> findLessonById(UUID id) throws SQLException {
        return facade.findLessonById(id);
    }
}
