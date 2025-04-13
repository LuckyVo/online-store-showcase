package ru.yandex.learn;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
public class AbstractTest {

    private static volatile boolean sharedSetupDone = false;
    private static final String POSTGRES_IMAGE = "postgres:15.3";

    public static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
            new PostgreSQLContainer<>(POSTGRES_IMAGE)
                    .withDatabaseName("shop")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withInitScript("init.sql")
                    .withReuse(true);


    @BeforeAll
    static void beforeAll() {
        if (!sharedSetupDone) {
            POSTGRE_SQL_CONTAINER.start();
            sharedSetupDone = true;
        }
    }

    @DynamicPropertySource
    static void dataSourceProp(@NonNull DynamicPropertyRegistry registry){
        registry.add("port", POSTGRE_SQL_CONTAINER::getFirstMappedPort);
    }

    @EventListener
    public void tearDown(ContextStoppedEvent ev){
        POSTGRE_SQL_CONTAINER.stop();
    }
}
