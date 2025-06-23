package server.data.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.data.entity.SchoolClass;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, UUID> {

    @Modifying
    @Query("DELETE FROM SchoolClass c WHERE c.id = :id")
    void deleteById(@Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM SchoolClass c WHERE c.id <> :id AND c.letter = :letter AND c.number = :number AND c.teacherId = :teacherId")
    boolean doesSchoolClassExist(@Param("id") UUID id, @Param("letter") String letter, @Param("number") int number, @Param("teacherId") UUID teacherId);

}

