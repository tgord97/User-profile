package com.iprody.userprofile.userprofileservice;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * AbstractIntegrationTest is an abstract class that provides common setup for integration tests.
 * It configures Spring Boot test environment with SpringBootTest, SpringExtension, and
 * RandomPort web environment. It also configures a PostgresSQL test container using Testcontainers
 * to provide a database for integration tests. AutoConfigureMockMvc is used to automatically
 * configure MockMvc for testing Spring MVC controllers.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractIntegrationTest {

    /**
     * PostgresSQL container instance provided by Testcontainers.
     */
    protected static final PostgreSQLContainer<?> CONTAINER;


    static {
        CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
        CONTAINER.start();
    }

    /**
     * Method to dynamically set PostgresSQL properties for Spring Boot.
     * It configures datasource URL, username, and password using dynamic properties.
     * @param registry DynamicPropertyRegistry to register dynamic properties.
     */
    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
    }
}
