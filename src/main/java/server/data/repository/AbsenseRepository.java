package server.data.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.data.entity.Absense;

import java.util.List;
import java.util.UUID;

@Repository
public interface AbsenseRepository extends JpaRepository<Absense, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Absense a SET a.isPresent = :isPresent WHERE a.id = :id")
    Absense updateAttendance(@Param("id") UUID id, @Param("isPresent") Boolean isPresent);

    List<Absense> findByPupilId(@Param("id") UUID pupilId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Absense a WHERE a.lessonId = :lessonId AND a.pupilId = :pupilId AND a.isPresent = :isPresent AND a.date = :date AND a.id <> :id")
    boolean existsDuplicateAbsence(@Param("lessonId") UUID lessonId, @Param("pupilId") UUID pupilId, @Param("isPresent") Boolean isPresent, @Param("date") java.sql.Date date, @Param("id") UUID id);
}


