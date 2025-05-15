package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.data.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public User save(User user) {
        user.setId(UUID.randomUUID());
        userRepository.save(user);
        return user;
    }

    public Optional<User> findUserByID(UUID id) {
        return userRepository.findUserById(id);
    }

    public void delete(UUID id) {
        userRepository.deleteUser(id);
    }

    public void update(UUID id) {
        userRepository.updateUser(id);
    }

    public List<User> findPupilsOfClass(UUID id) {
        return userRepository.getAllPupilsOfClass(id);
    }

    public boolean doesUserExist(UUID id) {
        return userRepository.doesUserExist(id);
    }
}
