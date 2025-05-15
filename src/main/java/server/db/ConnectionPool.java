package server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final List<Connection> availableConnections = new ArrayList<>();
    private final List<Connection> usedConnections = new ArrayList<>();
    private final int MAX_POOL_SIZE;


    public ConnectionPool(String jdbcUrl, String username, String password, int maxPoolSize) throws SQLException {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.MAX_POOL_SIZE = maxPoolSize;

        for (int i = 0; i < maxPoolSize; i++) {
            availableConnections.add(createConnection());
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public synchronized Connection connectToDataBase() throws SQLException {
        if (availableConnections.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                Connection newConnection = createConnection();
                usedConnections.add(newConnection);
                System.out.println("Created new connection: " + newConnection);
                return newConnection;
            } else {
                throw new SQLException("No available connections");
            }
        }

        Connection connection = availableConnections.remove(availableConnections.size() - 1);
        usedConnections.add(connection);
        System.out.println("Got connection: " + connection);
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) {
            if (!connection.isClosed() && connection.isValid(1)) {
                usedConnections.remove(connection);
                availableConnections.add(connection);
                System.out.println("Released connection: " + connection);
            } else {
                usedConnections.remove(connection);
                connection.close();
                System.out.println("Closed invalid connection: " + connection);
            }
        }
    }

    public synchronized void closeAllConnections() {
        for (Connection connection : availableConnections) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        for (Connection connection : usedConnections) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        availableConnections.clear();
        usedConnections.clear();
    }
}

