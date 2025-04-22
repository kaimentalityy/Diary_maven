package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Lesson;
import server.data.repository.LessonRepository;

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
        return lessonRepository.save(lesson);
    }

    public Optional<Lesson> findByLessonId(UUID lessonId) throws SQLException {
        return lessonRepository.findById(lessonId);
    }

    public List<Lesson> findAllLessonsByDate(LocalDateTime localDateTime) throws SQLException {
        return lessonRepository.findAllByDate(localDateTime);
    }

    public void deleteLesson(UUID lessonId) throws SQLException {
        lessonRepository.deleteById(lessonId);
    }

    public Optional<Lesson> findByClassId(UUID classId) throws SQLException {
        return lessonRepository.findByClassId(classId);
    }

    public List<Lesson> findBySubjectsId(UUID subjectId) throws SQLException {
        return lessonRepository.findBySubjectId(subjectId);
    }
}
