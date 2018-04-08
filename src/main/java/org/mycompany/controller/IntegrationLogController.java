package org.mycompany.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.mycompany.model.IntegrationLogEntity;
import org.mycompany.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IntegrationLogController {

    private final MyRepository myRepository;

    @Autowired
    public IntegrationLogController(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    @RequestMapping(value = "/integration-log")
    public ResponseEntity<List<IntegrationLogEntity>> getIntegrationLog(@RequestParam(value = "limit", required = false, defaultValue = "30") int limit,
                                                                        @RequestParam(value = "interval", required = false) String interval) throws IOException {
        if (interval != null) {
            Long[] timeBounds = new ObjectMapper().readValue(interval, new ObjectMapper().getTypeFactory().constructArrayType(Long.class));
            List<IntegrationLogEntity> entries = myRepository.findByTimeBetween(timeBounds[0], timeBounds[1]);

            return new ResponseEntity<>(sortByDecreaseTime(entries), HttpStatus.OK);
        }
        List<IntegrationLogEntity> entries = myRepository.findAll();

        return new ResponseEntity<>(sortByDecreaseTime(entries), HttpStatus.OK);
    }

    private List<IntegrationLogEntity> sortByDecreaseTime(List<IntegrationLogEntity> logEntities) {
        Comparator<IntegrationLogEntity> logEntityComparator =
                (logEntity1, logEntity2) -> Long.compare(logEntity2.getTime(), logEntity1.getTime());

        return logEntities.stream()
                .sorted(logEntityComparator)
                .collect(Collectors.toList());
    }

}
