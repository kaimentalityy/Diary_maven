package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.SubjectMapper;
import server.data.entity.SchoolClass;
import server.data.entity.Subject;
import server.data.repository.SubjectRepository;
import server.presentation.dto.request.SubjectRqDto;
import server.presentation.dto.response.SubjectRespDto;
import server.utils.exception.notfound.SubjectCustomNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final SchoolClassService schoolClassService;

    public Subject findById(UUID id) {
        return subjectRepository.findById(id).orElseThrow(() -> new SubjectCustomNotFoundException("subject not found"));
    }

    public SubjectRespDto addSubject(SubjectRqDto subjectRqDto) {

        SchoolClass schoolClass = schoolClassService.findClassById(subjectRqDto.classId());
        Subject subject = subjectMapper.toSubject(subjectRqDto, schoolClass);
        subject = subjectRepository.save(subject);

        return subjectMapper.toSubjectRespDto(subject);
    }
}
