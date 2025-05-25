package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.User;
import server.utils.exception.badrequest.InvalidColumnNameExceptionCustom;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.UserCustomNotFoundException;

import java.sql.*;
import java.util.*;

@Repository
public class UserRepository {

    ConnectionPool connectionPool;

    public UserRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public User save(User user) throws SQLException {
        insertUser(user);
        return user;
    }

    public User update(User user) throws SQLException {
        updateUser(user);
        return user;
    }

    public void updateUser(User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<String> allowedColumns = new ArrayList<>();
        allowedColumns.add("login");
        allowedColumns.add("password");
        allowedColumns.add("name");
        allowedColumns.add("lastname");
        allowedColumns.add("is_blocked");
        allowedColumns.add("role_id");
        allowedColumns.add("class_id");
        allowedColumns.add("age");

        System.out.println("Please enter the column you want to update (name, lastname, login, password, is_blocked, role_id, class_id, age): ");
        String column = scanner.nextLine();

        if (!allowedColumns.contains(column)) {
            throw new InvalidColumnNameExceptionCustom("Invalid column name. Allowed columns are: " + allowedColumns);
        }

        System.out.println("Please enter the updated value: ");
        String value = scanner.nextLine();

        String query = "UPDATE users SET " + column + " = ? WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, user.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to update user");
            }
        }
    }

    public void updateClassOfStudent(UUID classId, UUID id) throws SQLException {
        String query = "UPDATE users SET class_id = ? WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, classId);
            preparedStatement.setObject(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to update class");
            }
        }
    }

    public boolean doesUserExist(UUID id) throws SQLException {
        String query = "SELECT COUNT(*) \n" +
                "FROM users \n" +
                "WHERE name = ? \n" +
                "  AND lastname = ? \n" +
                "  AND login = ? \n" +
                "  AND password = ? \n" +
                "  AND role_id = ? \n" +
                "  AND is_blocked = ? \n" +
                " AND class_id = ? \n " +
                "  AND age = ? \n" +
                "  AND id <> ?;\n";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            User user = findUserById(id).orElseThrow(() -> new UserCustomNotFoundException(id));

            preparedStatement.setObject(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setObject(5, user.getRoleId());
            preparedStatement.setBoolean(6, user.isBlocked());
            preparedStatement.setObject(7, user.getClassId());
            preparedStatement.setObject(8, user.getAge());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        }
    }

    public Optional<User> findUserByLogin(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(UUID.fromString(resultSet.getString("id")));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("lastname"));
                user.setRoleId(UUID.fromString(resultSet.getString("role_id")));
                user.setBlocked(resultSet.getBoolean("is_blocked"));
                user.setClassId(UUID.fromString(resultSet.getString("class_id")));
                user.setAge(resultSet.getInt("age"));

                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    public Optional<User> findUserById(UUID id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(UUID.fromString(resultSet.getString("id")));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("lastname"));
                user.setRoleId(UUID.fromString(resultSet.getString("role_id")));
                user.setBlocked(resultSet.getBoolean("is_blocked"));
                user.setClassId(UUID.fromString(resultSet.getString("class_id")));
                user.setAge(resultSet.getInt("age"));

                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    public void deleteUser(UUID id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setString(1, id.toString());
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to delete user");
            }
        }
    }

    public void insertUser(User user) throws SQLException {
        String insertQuery = "INSERT INTO users (id, name, lastname, login, password, role_id, is_blocked, class_id, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setObject(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setObject(6, user.getRoleId());
            preparedStatement.setBoolean(7, user.isBlocked());
            preparedStatement.setObject(8, null);
            preparedStatement.setObject(9, user.getAge());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to insert user: " + user.getLogin());
            }
        }
    }


    public List<User> getAllPupilsOfClass(UUID id) throws SQLException {
        String query = "SELECT * FROM users WHERE class_id = ?";
        List<User> pupils = new ArrayList<>();

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(UUID.fromString(resultSet.getString("id")));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setRoleId(UUID.fromString(resultSet.getString("role_id")));
                    user.setBlocked(resultSet.getBoolean("is_blocked"));
                    user.setClassId(UUID.fromString(resultSet.getString("class_id")));
                    user.setAge(resultSet.getInt("age"));

                    pupils.add(user);
                }

            }
            return pupils;
        }
    }
}
