package com.example.demo.service;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;

@Service
@Slf4j
public class LiquibaseRollbackService {

    private static final Logger log = LoggerFactory.getLogger(LiquibaseRollbackService.class);
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${rollback.tag}")
    private String rollbackTagName;


    public void rollbackToTag(String tagName) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new liquibase.database.jvm.JdbcConnection(connection));

            ClassLoader classLoader = getClass().getClassLoader();

            Liquibase liquibase = new Liquibase("db/changelog-master.yaml", new ClassLoaderResourceAccessor(classLoader), database);

            if(!rollbackTagName.isEmpty())
            {
                liquibase.rollback(rollbackTagName, new Contexts());
            }
        } catch (Exception e) {
            log.error("Failed to rollback to tag: {}", tagName);
        }
    }
}
