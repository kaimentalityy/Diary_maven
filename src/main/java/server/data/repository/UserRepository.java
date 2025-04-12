package server.data.repository;

import server.db.ConnectionPool;
import server.data.entity.SchoolClass;
import server.data.entity.User;

import java.sql.*;
import java.util.*;

public class UserRepository {

    ConnectionPool connectionPool;

    public UserRepository() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
    }

    public User save(User user) throws SQLException {
        insertUser(user);
        return user;
    }

    public void updateUser(String login) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<String> allowedColumns = new ArrayList<>();
        Connection connection = null;
        allowedColumns.add("login");
        allowedColumns.add("password");
        allowedColumns.add("name");
        allowedColumns.add("lastname");
        allowedColumns.add("is_blocked");
        allowedColumns.add("role_id");
        allowedColumns.add("class_id");

        System.out.println("Please enter the column you want to update (name, lastname, login, password, is_blocked, role_id, class_id): ");
        String column = scanner.nextLine();

        if (!allowedColumns.contains(column)) {
            System.out.println("Invalid column name. Allowed columns are: " + allowedColumns);
        }

        System.out.println("Please enter the updated value: ");
        String value = scanner.nextLine();
        User user = findUserByLogin(login).orElse(null);
        if (user != null && user.getLogin() == null) {
            System.out.println("User not found with login: " + login);
        }

        String query = "UPDATE users SET " + column + " = ? WHERE id = ?";

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, user.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User successfully updated.");
            } else {
                System.out.println("Failed to update user.");
            }
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<User> findUserByLogin(String login) throws SQLException {
        User user = null;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            String query = "SELECT * FROM users WHERE login = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(UUID.fromString(resultSet.getString("id")));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("lastname"));
                user.setRole_id(UUID.fromString(resultSet.getString("role_id")));
                user.setBlocked(resultSet.getBoolean("is_blocked"));

                String classIdStr = resultSet.getString("class_id");
                if (classIdStr != null && !resultSet.wasNull()) {
                    user.setClass_id(UUID.fromString(classIdStr));
                } else {
                    user.setClass_id(null);
                }
            }

            return Optional.ofNullable(user);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<User> findUserById(UUID id) throws SQLException {
        User user = null;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            String query = "SELECT * FROM users WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(UUID.fromString(resultSet.getString("id")));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("lastname"));
                user.setRole_id(UUID.fromString(resultSet.getString("role_id")));
                user.setBlocked(resultSet.getBoolean("is_blocked"));

                String classIdStr = resultSet.getString("class_id");
                if (classIdStr != null && !resultSet.wasNull()) {
                    user.setClass_id(UUID.fromString(classIdStr));
                } else {
                    user.setClass_id(null);
                }
            }

            return Optional.ofNullable(user);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<User> findPupilByClassId(UUID class_id) throws SQLException {
        User user = null;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            String query = "SELECT * FROM users WHERE class_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, class_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(UUID.fromString(resultSet.getString("id")));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("lastname"));
                user.setRole_id(UUID.fromString(resultSet.getString("role_id")));
                user.setBlocked(resultSet.getBoolean("is_blocked"));
                user.setClass_id(UUID.fromString(resultSet.getString("class_id")));
            }

            return Optional.ofNullable(user);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public void deleteUser(String login) throws SQLException {
        String query = "DELETE FROM users WHERE login = ?";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, login);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully: " + login);
                connectionPool.releaseConnection(connection);
            } else {
                System.out.println("User not found: " + login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public void insertUser(User user) throws SQLException {
        String insertQuery = "INSERT INTO users (id, name, lastname, login, password, role_id, is_blocked, class_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setObject(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setObject(6, user.getRole_id());
            preparedStatement.setBoolean(7, user.isBlocked());
            preparedStatement.setObject(8, user.getClass_id());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User successfully inserted: " + user.getLogin());
                connectionPool.releaseConnection(connection);
            } else {
                System.out.println("Failed to insert absence user: " + user.getLogin());
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public List<User> getAllPupilsOfClass(SchoolClass schoolClass) throws SQLException {
        String query = "SELECT * FROM users WHERE class_id = ?";
        List<User> pupils = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, schoolClass.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(findPupilByClassId(schoolClass.getId()).get().getId());
                    user.setLogin(findPupilByClassId(schoolClass.getId()).get().getLogin());
                    user.setPassword(findPupilByClassId(schoolClass.getId()).get().getPassword());
                    user.setName(findPupilByClassId(schoolClass.getId()).get().getName());
                    user.setLastname(findPupilByClassId(schoolClass.getId()).get().getLastname());
                    user.setRole_id(findPupilByClassId(schoolClass.getId()).get().getRole_id());
                    user.setBlocked(findPupilByClassId(schoolClass.getId()).get().isBlocked());
                    user.setClass_id(findPupilByClassId(schoolClass.getId()).get().getClass_id());
                    pupils.add(user);
                }
            }
            return pupils;
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}
