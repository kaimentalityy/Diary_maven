package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.Subject;
import server.presentation.dto.request.SubjectRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.SubjectRespDto;

import java.util.Optional;

@Component
public class SubjectMapper {

    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

    public SubjectRespDto toSubjectRespDto(Subject subject) {
        return new SubjectRespDto(subject.getId(), subject.getName());
    }

    public Subject toSubject(SubjectRqDto subjectRqDto) {
        Subject subject = new Subject();
        subject.setName(subjectRqDto.name());
        return subject;
    }
}
