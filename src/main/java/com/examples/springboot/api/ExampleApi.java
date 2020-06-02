package com.examples.springboot.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleApi {

    @GetMapping(path = "/hello")
    public String getHello() {
        return "Hello World";
    }
}
