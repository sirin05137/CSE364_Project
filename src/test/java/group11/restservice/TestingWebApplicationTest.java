package group11.restservice;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestingWebApplicationTest {

    @Test
    public void contextLoads() {
    }

    @Test
    public void applicationContextLoaded() {
    }

    // To test RecommendApp.java
    @Test
    public void applicationContextTest() {
        RecommendApp.main(new String[] {});
    }

}