package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.data.entity.Subject;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
}
