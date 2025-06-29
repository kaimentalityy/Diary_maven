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
        return lessonRepository.save(lesson);
    }

    public Lesson findById(UUID lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonCustomNotFoundException("Lesson not found with ID: " + lessonId));
    }

    public List<Lesson> findAllLessonsByDate(LocalDateTime localDateTime) {
        return lessonRepository.findAllByDate(localDateTime);
    }

    public void deleteLesson(UUID lessonId) {
        if (findById(lessonId) == null) {
            throw new LessonCustomNotFoundException(lessonId);
        } else {
            lessonRepository.deleteById(lessonId);
        }
    }

    public List<Lesson> findBySubjectsId(UUID subjectId) {
        return lessonRepository.findBySubject_Id(subjectId);
    }
}

