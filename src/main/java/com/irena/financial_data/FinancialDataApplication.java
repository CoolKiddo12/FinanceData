package com.irena.financial_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCassandraRepositories(basePackages = { "com.irena.financial_data.repository.cassandra" })
@EnableJpaRepositories(basePackages = { "com.irena.financial_data.repository.postgres" })
public class FinancialDataApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinancialDataApplication.class, args);
	}
}
