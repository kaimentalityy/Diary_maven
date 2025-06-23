package server.business.mapper;

import org.mapstruct.Mapper;
import server.data.entity.Lesson;
import server.presentation.dto.request.LessonRqDto;
import server.presentation.dto.response.LessonRespDto;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    LessonRespDto toLessonRespDto(Lesson lesson);

    Lesson toLesson(LessonRqDto lessonRqDto);
}
