package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.data.entity.TeacherOfSubject;

import java.util.*;

@Repository
public interface TeacherOfSubjectRepository extends JpaRepository<TeacherOfSubject, UUID> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TeacherOfSubject t WHERE t.id <> :id AND t.subjectId = :subjectId AND t.teacherId = :teacherId")
    boolean doesTeacherExist(@Param("id") UUID id, @Param("subjectId") UUID subjectId, @Param("teacherId") UUID teacherId);

}
