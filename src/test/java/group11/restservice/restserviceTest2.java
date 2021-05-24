package group11.restservice;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import group11.restservice.controller.HelloController;
import group11.restservice.model.UserData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.Assert.notNull;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//WebLayerTest
@WebMvcTest(HelloController.class)
public class restserviceTest2 {

    @Autowired( required = true )
    private MockMvc mockMvc;
    /*
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World!")));
    }

     */
}


/*

//2
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HelloController.class)
@SpringBootTest
@AutoConfigureMockMvc
class SmokeTest {

    @Autowired
    private HelloController controller;
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        //assertThat(controller).isNotNull();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World!"));
    }
}

//3
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Hello World!");
    }
}

//4

@SpringBootTest
@AutoConfigureMockMvc
class TestingWebApplicationTest2 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World!")));
    }
}

//5
@WebMvcTest(HelloController.class)
class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    //@MockBean
    //private GreetingService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        //when(service.greet()).thenReturn("Hello, Mock");
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World!")));
    }
}

//6
@JsonTest
class UserDataJsonTest {
    @Autowired
    private JacksonTester<UserData> json;

    @Test
    public void testSerialize() throws Exception {

        UserData userDetails = new UserData("F", "25", "Grad Student", "Action");

        JsonContent<UserData> result = this.json.write(userDetails);

        assertThat(result).hasJsonPathStringValue("$.gender");
        assertThat(result).extractingJsonPathStringValue("$.gender").isEqualTo("F");

        //assertThat(result).doesNotHaveJsonPath("$.enabled");
    }
}

@RunWith(SpringJUnit4ClassRunner.class)
class sibal {
    @Autowired
    private HelloController controller;

    @Test
    public void sibaltest(){
        Assert.assertNotNull(controller.helloWorld());
    }
}

*/