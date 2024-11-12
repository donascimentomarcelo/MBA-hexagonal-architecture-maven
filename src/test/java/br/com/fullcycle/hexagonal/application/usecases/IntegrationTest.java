package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.infrastructure.MbaHexagonalArchitectureMavenApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = MbaHexagonalArchitectureMavenApplication.class)
public abstract class IntegrationTest {
}
