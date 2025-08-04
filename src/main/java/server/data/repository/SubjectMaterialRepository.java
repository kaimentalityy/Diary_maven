package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.data.entity.SubjectMaterial;

import java.util.UUID;

@Repository
public interface SubjectMaterialRepository extends JpaRepository<SubjectMaterial, UUID> {
}
