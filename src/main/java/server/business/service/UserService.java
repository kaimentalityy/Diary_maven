package server.business.service;

import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.data.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;

    public UserService() throws SQLException {
        this.userRepository = new UserRepository();
    }

    public Optional<User> findUserByLogin(String login) throws SQLException {
        return userRepository.findUserByLogin(login);
    }

    public User save(User user) throws SQLException {
        user.setId(UUID.randomUUID());
        userRepository.save(user);
        return user;
    }

    public Optional<User> findUserByID(UUID id) throws SQLException {
        return userRepository.findUserById(id);
    }

    public void delete(User user) throws SQLException {
        userRepository.deleteUser(user.getLogin());
    }

    public void update(String login) throws SQLException {
        userRepository.updateUser(login);
    }

    public List<User> findPupilsOfClass(SchoolClass schoolClass) throws SQLException {
        return userRepository.getAllPupilsOfClass(schoolClass);
    }
}
