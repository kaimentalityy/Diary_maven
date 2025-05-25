package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Lesson;
import server.data.repository.LessonRepository;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.LessonCustomNotFoundException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public Lesson addLesson(Lesson lesson) {
        lesson.setId(UUID.randomUUID());
        try {
            return lessonRepository.save(lesson);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to add lesson");
        }
    }

    public Lesson findByLessonId(UUID lessonId) {
        try {
            return lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new LessonCustomNotFoundException("Lesson not found with ID: " + lessonId));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find lesson with ID: " + lessonId);
        }
    }

    public List<Lesson> findAllLessonsByDate(LocalDateTime localDateTime) {
        try {
            return lessonRepository.findAllByDate(localDateTime);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find lessons for date: " + localDateTime);
        }
    }

    public void deleteLesson(UUID lessonId) {
        try {
            if (lessonRepository.doesLessonExist(lessonId)) {
                lessonRepository.deleteById(lessonId);
            } else {
                throw new LessonCustomNotFoundException("Lesson not found with ID: " + lessonId);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to delete lesson with ID: " + lessonId);
        }
    }

    public Optional<Lesson> findByClassId(UUID classId) {
        try {
            return lessonRepository.findByClassId(classId);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find lesson by class ID: " + classId);
        }
    }

    public List<Lesson> findBySubjectsId(UUID subjectId) {
        try {
            return lessonRepository.findBySubjectId(subjectId);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find lessons by subject ID: " + subjectId);
        }
    }
}

