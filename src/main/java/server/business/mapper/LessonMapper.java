package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.Lesson;
import server.presentation.dto.request.LessonRqDto;
import server.presentation.dto.response.LessonRespDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;

import java.util.Optional;

@Component
public class LessonMapper {

    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

    public LessonRespDto toLessonRespDto(Lesson lesson) {
        return new LessonRespDto(lesson.getId(), lesson.getClassId(), lesson.getTeacherOfSubjectId(), lesson.getDate(), lesson.getSubjectId());
    }

    public Lesson toLesson(LessonRqDto lessonRqDto) {
        Lesson lesson = new Lesson();
        lesson.setClassId(lessonRqDto.class_id());
        lesson.setTeacherOfSubjectId(lessonRqDto.teacher_of_subject_id());
        lesson.setDate(lessonRqDto.date());
        lesson.setSubjectId(lessonRqDto.subject_id());
        return lesson;
    }
}
