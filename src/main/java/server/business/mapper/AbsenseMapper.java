package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.Absense;
import server.presentation.dto.request.AbsenseRqDto;
import server.presentation.dto.request.UpdateAbsenseRqDto;
import server.presentation.dto.request.UpdateTeacherRqDto;
import server.presentation.dto.response.AbsenseRespDto;
import server.presentation.dto.response.ErrorDto;

import java.util.Optional;

@Component
public class AbsenseMapper {

    public AbsenseRespDto toAttendanceRespDto(Absense absense) {
        return new AbsenseRespDto(absense.getId(), absense.getLessonId(), absense.getPupilId(), absense.getIsPresent(), absense.getDate());
    }

    public Absense toAttendance(AbsenseRqDto absenseRqDto) {
        Absense absense = new Absense();
        absense.setIsPresent(absenseRqDto.is_absent());
        absense.setLessonId(absenseRqDto.lesson_id());
        absense.setPupilId(absenseRqDto.pupil_id());
        absense.setDate(absenseRqDto.date());
        return absense;
    }

    public Absense toAttendanceForUpdate(UpdateAbsenseRqDto updateAbsenseRqDto) {
        Absense absense = new Absense();
        absense.setId(updateAbsenseRqDto.id());
        absense.setIsPresent(updateAbsenseRqDto.is_absent());
        absense.setLessonId(updateAbsenseRqDto.lesson_id());
        absense.setPupilId(updateAbsenseRqDto.pupil_id());
        absense.setDate(updateAbsenseRqDto.date());
        return absense;
    }
}
