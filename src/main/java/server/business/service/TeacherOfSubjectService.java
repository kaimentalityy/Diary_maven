package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.TeacherOfSubject;
import server.data.repository.TeacherOfSubjectRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherOfSubjectService {

    private final TeacherOfSubjectRepository teacherOfSubjectRepository;

    public TeacherOfSubject addTeacher(TeacherOfSubject teacherOfSubject) {
        teacherOfSubject.setId(UUID.randomUUID());
        return teacherOfSubjectRepository.save(teacherOfSubject);
    }

    public void deleteTeacher(UUID id) {
        teacherOfSubjectRepository.deleteById(id);
    }

    public void updateTeacher(UUID id) {
        teacherOfSubjectRepository.updateTeacher(id);
    }

    public Optional<TeacherOfSubject> findById(UUID id) {
        return teacherOfSubjectRepository.findTeacherById(id);
    }

    public boolean doesTeacherExist(UUID id) {
        return teacherOfSubjectRepository.doesTeacherExist(id);
    }
}
