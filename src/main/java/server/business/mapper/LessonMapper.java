package server.business.mapper;

import server.data.entity.Lesson;
import server.presentation.dto.request.LessonRqDto;
import server.presentation.dto.response.LessonRespDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;

import java.util.Optional;

public class LessonMapper {

    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

    public LessonRespDto toLessonRespDto(Lesson lesson) {
        return new LessonRespDto(lesson.getId(), lesson.getClass_id(), lesson.getTeacher_of_subject_id(), lesson.getDate(), lesson.getSubject_id());
    }

    public Lesson toLesson(LessonRqDto lessonRqDto) {
        Lesson lesson = new Lesson();
        lesson.setClass_id(lessonRqDto.class_id());
        lesson.setTeacher_of_subject_id(lessonRqDto.teacher_of_subject_id());
        lesson.setDate(lessonRqDto.date());
        lesson.setSubject_id(lessonRqDto.subject_id());
        return lesson;
    }
}
