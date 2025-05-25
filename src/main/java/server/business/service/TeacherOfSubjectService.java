package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.TeacherOfSubject;
import server.data.repository.TeacherOfSubjectRepository;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.TeacherCustomNotFoundException;

import java.sql.SQLException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherOfSubjectService {

    private final TeacherOfSubjectRepository teacherOfSubjectRepository;

    public TeacherOfSubject addTeacher(TeacherOfSubject teacherOfSubject) {
        teacherOfSubject.setId(UUID.randomUUID());
        try {
            return teacherOfSubjectRepository.save(teacherOfSubject);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to save teacher-subject mapping");
        }
    }

    public void deleteTeacher(UUID id) {
        try {
            if (teacherOfSubjectRepository.doesTeacherExist(id)) {
                teacherOfSubjectRepository.deleteById(id);
            } else {
                throw new TeacherCustomNotFoundException("Teacher not found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to delete teacher-subject mapping for ID: " + id);
        }
    }

    public TeacherOfSubject updateTeacher(TeacherOfSubject teacherOfSubject) {
        try {
            if (teacherOfSubjectRepository.doesTeacherExist(teacherOfSubject.getId())) {
                return teacherOfSubjectRepository.update(teacherOfSubject);
            } else {
                throw new TeacherCustomNotFoundException("Teacher not found with ID: " + teacherOfSubject.getId());
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to update teacher-subject mapping for ID: " + teacherOfSubject.getId());
        }
    }

    public TeacherOfSubject findById(UUID id) {
        try {
            return teacherOfSubjectRepository.findTeacherById(id)
                    .orElseThrow(() -> new TeacherCustomNotFoundException("Teacher not found with ID: " + id));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to retrieve teacher-subject mapping for ID: " + id);
        }
    }
}
