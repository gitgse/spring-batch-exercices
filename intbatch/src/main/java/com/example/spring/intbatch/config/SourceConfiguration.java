package com.example.spring.intbatch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("application.properties")
public class SourceConfiguration {

	@Bean
	@Qualifier("bankingDataSource")
	public DataSource getDataSource(
			@Value("${db.driver}") String driverClassName,
			@Value("${db.url}") String url,
			@Value("${db.username}") String userName,
			@Value("${db.password}") String password) {
		var dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}
}
