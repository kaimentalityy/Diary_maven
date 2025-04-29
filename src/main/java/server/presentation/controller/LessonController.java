package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    private final MainFacade facade;

    @PostMapping
    public ResponseDto<LessonRespDto> createLesson(@RequestBody LessonRqDto lessonRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(lessonRqDto);

        return facade.assignLesson(lessonRqDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteLesson(@PathVariable UUID id) throws SQLException, ConstraintViolationException {

        ResponseDto<Lesson> lesson = facade.findLessonById(id);

        if (findLessonById(lesson.getResult().orElse(null).getId()).getResult().isPresent()) {
            Validator.notNull(lesson);
            return facade.removeLesson(lesson.getResult().orElse(null));
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Lesson not found"));
    }

    @GetMapping("/{localDateTime}")
    public List<Lesson> findAllLessonsByDate(@PathVariable LocalDateTime localDateTime) throws SQLException {
        return facade.findAllLessonsByDate(localDateTime);
    }

    @GetMapping("/{id}")
    public ResponseDto<Lesson> findLessonById(@PathVariable UUID id) throws SQLException {
        return facade.findLessonById(id);
    }
}
