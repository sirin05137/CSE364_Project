package group11.restservice.controller;

import group11.restservice.model.RecoData;
import group11.restservice.repository.RecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class HelloController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private RecoRepository recoRepository;

    public HelloController(RecoRepository recoRepository) throws IOException {
        this.recoRepository = recoRepository;
    }

    @RequestMapping("/index.html")
    public List<RecoData> helloWorld() {
        return this.recoRepository.findAll();
    }

}