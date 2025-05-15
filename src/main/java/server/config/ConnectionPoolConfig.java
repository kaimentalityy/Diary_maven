package server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.db.ConnectionPool;

import java.sql.SQLException;

@Configuration
public class ConnectionPoolConfig {

    @Bean
    public ConnectionPool connectionPool(
            @Value("${db.url}") String url,
            @Value("${db.username}") String username,
            @Value("${db.password}") String password,
            @Value("${db.pool.size}") int poolSize
    ) throws SQLException {
        return new ConnectionPool(url, username, password, poolSize);
    }
}
