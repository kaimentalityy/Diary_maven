package server.business.service;

import org.springframework.stereotype.Service;
import server.data.entity.TeacherOfSubject;
import server.data.repository.TeacherOfSubjectRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherOfSubjectService {

    private final TeacherOfSubjectRepository teacherOfSubjectRepository;

    public TeacherOfSubjectService() throws SQLException {
        teacherOfSubjectRepository = new TeacherOfSubjectRepository();
    }

    public TeacherOfSubject addTeacher(TeacherOfSubject teacherOfSubject) throws SQLException {
        teacherOfSubject.setId(UUID.randomUUID());
        return teacherOfSubjectRepository.save(teacherOfSubject);
    }

    public void deleteTeacher(TeacherOfSubject teacherOfSubject) throws SQLException {
        teacherOfSubjectRepository.deleteById(teacherOfSubject.getId());
    }

    public void updateTeacher(UUID id) throws SQLException {
        teacherOfSubjectRepository.updateTeacher(id);
    }

    public Optional<TeacherOfSubject> findById(UUID id) throws SQLException {
        return teacherOfSubjectRepository.findTeacherById(id);
    }
}
