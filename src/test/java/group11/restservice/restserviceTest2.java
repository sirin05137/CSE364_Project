package group11.restservice;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import group11.restservice.controller.HelloController;
import group11.restservice.controller.UserDataController;
import group11.restservice.model.UserData;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;


//2
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HelloController.class)
@SpringBootTest
//@AutoConfigureMockMvc
public class restserviceTest2 {

    /*

    //@Autowired
    //private MockMvc mockMvc;
    //private HelloController controller;

    @Before
    public void setUp() throws Exception {
        //mockMvc = MockMvcBuilders.standaloneSetup(HelloController).build();
    }

    @SneakyThrows
    @Test
    public void contextLoads() {
        String response = "";
        HelloController controller = new HelloController();
        response = controller.helloWorld();

        Assertions.assertEquals(response, "Hello World!");
        //assertThat(controller).isNotNull();
        //Assertions.assertEquals(controller.helloWorld(),"Hello World!");
        //System.out.println(controller.helloWorld());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World!"));
    }

    @SneakyThrows
    @Test
    public void UDcontrollerTest () {
        UserDataController udcontroller = new UserDataController();

        String attributeName = "sessionAttr";
        String attribute = "value";
        WebDataBinder binder = new WebDataBinder(attribute, attributeName);
        udcontroller.initBinder(binder);
    }

    @SneakyThrows
    @Test
    public void UDcontrollerTest2 () {
        UserDataController udcontroller = new UserDataController();

        UserData ud = new UserData("F", "25", "Grad", "Action");
        //udcontroller.addUserData(ud);
        Assertions.assertEquals(udcontroller.addUserData(ud),ud);
        ObjectMapper objectMapper = new ObjectMapper();

        //String request = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ud);
        //System.out.println(request);

        String request = "{}";
        //Assertions.assertAll(udcontroller.getUserRecommendations(request));
    }


    */
}

/*
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

*/