package group11.restservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    // localhost:8080/api/test
    //@RequestMapping(value = "/api/test", method = RequestMethod.GET)
    @GetMapping("/api/test")
    @ResponseStatus(value = HttpStatus.OK)
    public String getApiTest () {
        return "{\"result\":\"ok\"}";
    }
}
