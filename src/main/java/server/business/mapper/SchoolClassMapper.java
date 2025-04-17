package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.SchoolClass;
import server.presentation.dto.request.SchoolClassRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.SchoolClassRespDto;

import java.util.Optional;

@Component
public class SchoolClassMapper {
    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

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
