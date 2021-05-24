package group11.restservice;

import group11.restservice.controller.HelloController;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.AssertJUnit.assertNotNull;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HelloController.class)
public class Test1 {


    @LocalServerPort
    private int port;


    @Autowired
    private TestRestTemplate restTemplate;
    private MockMvc mvc;
    private HelloController helloController;

    public Test1(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
    @Test
    public void test1() throws Exception {
        //assertThat(helloController).isNotNull();
        //Assertions.assertNotNull(restTemplate);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class)).contains("Hello, World");
    }
    */




    /*
    @Autowired
    private MockMvc mvc;
    private HelloController helloController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(helloController).isNotNull();
    }

    @Test
    void hello() throws Exception {
        //Assertions.assertThat(helloworld.()).isEqualTo("hi");

        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assert.assertSame(content().toString(), "welcome");

        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                .andExpect(content().string(equalTo("Hello World!")));
    }
    */

}