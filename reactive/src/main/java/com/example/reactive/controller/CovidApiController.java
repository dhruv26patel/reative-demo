package com.example.reactive.controller;

import com.example.reactive.service.ReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class CovidApiController {

    @Autowired
    private ReactiveService reactiveService;

    @RequestMapping("/data")
    public Mono<String> getData() {
        return reactiveService.getData();
    }
}
