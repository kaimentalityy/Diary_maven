package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.SchoolClass;
import server.presentation.dto.request.SchoolClassRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.SchoolClassRespDto;

import java.util.Optional;

@Component
public class SchoolClassMapper {

    public SchoolClassRespDto toSchoolClassRespDto(SchoolClass schoolClass) {
        return new SchoolClassRespDto(schoolClass.getId(), schoolClass.getLetter(), schoolClass.getNumber(), schoolClass.getTeacherId());
    }

    public SchoolClass toSchoolClass(SchoolClassRqDto schoolClassRqDto) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setLetter(schoolClassRqDto.letter());
        schoolClass.setNumber(schoolClassRqDto.number());
        schoolClass.setTeacherId(schoolClassRqDto.teacher_id());
        return schoolClass;
    }
}
