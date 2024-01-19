package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DatabaseConfig {

        
	@Bean
	public DataSource dataSource() {
            DriverManagerDataSource driver = new DriverManagerDataSource();
            driver.setDriverClassName("org.postgresql.Driver");
            driver.setUrl("jdbc:postgresql://localhost:5432/db3");
            driver.setUsername("user1");
            driver.setPassword("password");
            return driver;
          }
        

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

}
