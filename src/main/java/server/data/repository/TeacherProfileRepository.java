package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.data.entity.TeacherProfile;

import java.util.UUID;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, UUID> {
}
