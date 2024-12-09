package com.example.demo.service;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

@Service
@Slf4j
public class LiquibaseRollbackService {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;


    public void rollbackToTag(String tagName) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new liquibase.database.jvm.JdbcConnection(connection));

            ClassLoader classLoader = getClass().getClassLoader();

            Liquibase liquibase = new Liquibase("db/changelog-master.yaml", new ClassLoaderResourceAccessor(classLoader), database);

            liquibase.rollback(tagName, new Contexts());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to rollback to tag: " + tagName, e);
        }
    }
}
