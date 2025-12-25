package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.LessonMapper;
import server.data.entity.Lesson;
import server.data.entity.SchoolClass;
import server.data.entity.Subject;
import server.data.entity.TeacherAssignment;
import server.data.repository.LessonRepository;
import server.presentation.dto.request.LessonRqDto;
import server.presentation.dto.response.LessonRespDto;
import server.utils.exception.notfound.LessonCustomNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final SchoolClassService schoolClassService;
    private final TeacherOfSubjectService teacherOfSubjectService;
    private final SubjectService subjectService;

    public LessonRespDto assignLesson(LessonRqDto lessonRqDto) {

        Subject subject = subjectService.findById(lessonRqDto.subjectId());
        SchoolClass schoolClass = schoolClassService.findClassById(lessonRqDto.classId());
        TeacherAssignment teacherAssignment =  teacherOfSubjectService.findById(lessonRqDto.teacherOfSubjectId());

        Lesson lesson = lessonMapper.toLesson(lessonRqDto, schoolClass, subject, teacherAssignment);

        lesson = addLesson(lesson);

        return lessonMapper.toLessonRespDto(lesson);
    }

    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson findById(UUID lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonCustomNotFoundException("Lesson not found with ID: " + lessonId));
    }

    public List<Lesson> findAllLessonsByDate(LocalDateTime localDateTime) {
        return lessonRepository.findAllByLessonDate(localDateTime);
    }

    public void deleteLesson(UUID lessonId) {
        if (findById(lessonId) == null) {
            throw new LessonCustomNotFoundException(lessonId);
        } else {
            lessonRepository.deleteById(lessonId);
        }
    }

    public List<Lesson> findBySubjectsId(Subject subject) {
        return lessonRepository.findBySubject(subject);
    }
}

