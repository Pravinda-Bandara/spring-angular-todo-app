package lk.ijse.dep11.todo.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/dep11_todo_app");
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }
}
