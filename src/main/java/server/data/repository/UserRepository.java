package server.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.data.entity.User;

import java.sql.*;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLogin(String login) throws SQLException;

    List<User> findByClassId(UUID classId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id <> :id AND u.name = :name AND u.lastname = :lastname AND u.login = :login AND u.password = :password AND u.roleId = :roleId AND u.isBlocked = :isBlocked AND u.classId = :classId AND u.age = :age")
    boolean doesUserExist(@Param("id") UUID id,
                          @Param("name") String name,
                          @Param("lastname") String lastname,
                          @Param("login") String login,
                          @Param("password") String password,
                          @Param("roleId") UUID roleId,
                          @Param("isBlocked") boolean isBlocked,
                          @Param("classId") UUID classId,
                          @Param("age") int age);
}

