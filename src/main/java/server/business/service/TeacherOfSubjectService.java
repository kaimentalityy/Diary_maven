package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.TeacherOfSubject;
import server.data.repository.SubjectRepository;
import server.data.repository.TeacherOfSubjectRepository;
import server.utils.exception.notfound.TeacherCustomNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherOfSubjectService {

    private final TeacherOfSubjectRepository teacherOfSubjectRepository;
    private final SubjectRepository subjectRepository;

    public TeacherOfSubject addTeacher(TeacherOfSubject teacherOfSubject) {
        return teacherOfSubjectRepository.save(teacherOfSubject);
    }

    public void deleteTeacher(UUID id) {
        teacherOfSubjectRepository.deleteById(id);
    }

    public TeacherOfSubject updateTeacher(TeacherOfSubject updatedData) {
        TeacherOfSubject existing = teacherOfSubjectRepository.findById(updatedData.getId())
                .orElseThrow(() -> new TeacherCustomNotFoundException("Teacher not found with ID: " + updatedData.getId()));

        existing.setSubject(updatedData.getSubject());
        existing.setTeacher(updatedData.getTeacher());

        return teacherOfSubjectRepository.save(existing);
    }

    public TeacherOfSubject findById(UUID id) {
        return teacherOfSubjectRepository.findById(id)
                .orElseThrow(() -> new TeacherCustomNotFoundException("Teacher not found with ID: " + id));
    }
}
