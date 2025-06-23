package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.data.entity.Lesson;
import server.utils.exception.notfound.SchoolClassCustomNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    Optional<Lesson> findByClassId(UUID classId) throws SchoolClassCustomNotFoundException;

    List<Lesson> findBySubjectId(UUID subjectId) throws SQLException;

    List<Lesson> findAllByDate(LocalDateTime localDateTime) throws SQLException;

    void deleteById(UUID id);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Lesson l WHERE l.id <> :id AND l.date = :date AND l.subjectId = :subjectId AND l.teacherOfSubjectId = :teacherOfSubjectId AND l.classId = :classId")
    boolean doesLessonExist(@Param("id") UUID id, @Param("date") LocalDateTime date, @Param("subjectId") UUID subjectId, @Param("teacherOfSubjectId") UUID teacherOfSubjectId, @Param("classId") UUID classId);
}
