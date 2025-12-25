package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.TeacherMapper;
import server.data.entity.Subject;
import server.data.entity.TeacherAssignment;
import server.data.entity.User;
import server.data.repository.TeacherOfSubjectRepository;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.response.TeacherRespDto;
import server.utils.exception.notfound.TeacherCustomNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherOfSubjectService {

    private final TeacherOfSubjectRepository teacherOfSubjectRepository;
    private final TeacherMapper teacherMapper;
    private final UserService  userService;
    private final SubjectService subjectService;

    public TeacherRespDto addTeacher(TeacherRqDto teacherRqDto) {

        Subject subject = subjectService.findById(teacherRqDto.subjectId());
        User teacher = userService.findUserByID(teacherRqDto.teacherId());
        TeacherAssignment teacherAssignment = teacherMapper.toTeacherOfSubject(teacherRqDto, teacher, subject);

        teacherAssignment = addTeacher(teacherAssignment);

        return teacherMapper.toTeacherRespDto(teacherAssignment);
    }

    public TeacherAssignment addTeacher(TeacherAssignment teacherAssignment) {
        return teacherOfSubjectRepository.save(teacherAssignment);
    }

    public void deleteTeacher(UUID id) {
        teacherOfSubjectRepository.deleteById(id);
    }

    public TeacherAssignment updateTeacher(TeacherAssignment updatedData) {
        TeacherAssignment existing = teacherOfSubjectRepository.findById(updatedData.getId())
                .orElseThrow(() -> new TeacherCustomNotFoundException("Teacher not found with ID: " + updatedData.getId()));

        existing.setSubject(updatedData.getSubject());
        existing.setTeacher(updatedData.getTeacher());

        return teacherOfSubjectRepository.save(existing);
    }

    public TeacherAssignment findById(UUID id) {
        return teacherOfSubjectRepository.findById(id)
                .orElseThrow(() -> new TeacherCustomNotFoundException("Teacher not found with ID: " + id));
    }
}
