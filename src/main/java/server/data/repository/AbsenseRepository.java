package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.data.entity.Attendance;
import server.data.entity.Lesson;
import server.data.entity.StudentProfile;

import java.util.List;
import java.util.UUID;

@Repository
public interface AbsenseRepository extends JpaRepository<Attendance, UUID> {
    List<Attendance> findByPupilId(UUID pupilId);

    boolean existsByLessonAndPupilAndPresentAndIdNot(Lesson lesson, StudentProfile pupil, Boolean present, UUID id);
}
