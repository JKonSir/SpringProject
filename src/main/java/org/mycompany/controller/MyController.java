package org.mycompany.controller;

import org.mycompany.integration.MyRestTemplateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyController {

    private final MyRestTemplateClient restTemplateClient;

    @Autowired
    public MyController(MyRestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    @RequestMapping(value = "/rest-template/get", method = RequestMethod.GET)
    public ResponseEntity<String> getWithRestTemplate() {
        String response = restTemplateClient.get();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/test-html", method = RequestMethod.GET)
    public ModelAndView getView() {
        List<String> numbers = new ArrayList<>();
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");

        ModelAndView modelAndView = new ModelAndView("testHtml");
        modelAndView.addObject("numbers", numbers);

        return modelAndView;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<String> getForForm(@RequestParam("number") String number) {
        return new ResponseEntity<>(number, HttpStatus.OK);
    }

}
