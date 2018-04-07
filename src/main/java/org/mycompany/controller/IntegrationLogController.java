package org.mycompany.controller;

import org.mycompany.model.IntegrationLogEntity;
import org.mycompany.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IntegrationLogController {

    private final MyRepository myRepository;

    @Autowired
    public IntegrationLogController(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    @RequestMapping(value = "/integration-log")
    public ResponseEntity<List<IntegrationLogEntity>> getIntegrationLog() {
        List<IntegrationLogEntity> entries = myRepository.findAll();

        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

}
