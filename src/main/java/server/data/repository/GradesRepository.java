package server.data.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.data.entity.Grades;

import java.util.List;
import java.util.UUID;

@Repository
public interface GradesRepository extends JpaRepository<Grades, UUID> {

    @Query("SELECT g.grade FROM Grades g WHERE g.pupilId = :pupilId AND g.lessonId IN :lessonIds")
    List<String> getAllGradesOfPupil(@Param("pupilId") UUID pupilId, @Param("lessonIds") List<UUID> lessonIds);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Grades g WHERE g.id <> :id AND g.pupilId = :pupilId AND g.lessonId = :lessonId AND g.grade = :grade")
    boolean doesGradesExist(@Param("id") UUID id, @Param("pupilId") UUID pupilId, @Param("lessonId") UUID lessonId, @Param("grade") String grade);
}
