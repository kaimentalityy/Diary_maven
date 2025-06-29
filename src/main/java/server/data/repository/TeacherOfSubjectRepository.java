package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.data.entity.TeacherOfSubject;

import java.util.UUID;

@Repository
public interface TeacherOfSubjectRepository extends JpaRepository<TeacherOfSubject, UUID> {
}
