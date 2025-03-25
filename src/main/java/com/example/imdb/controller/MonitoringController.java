package com.example.imdb.controller;

import com.example.imdb.service.RequestCounterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
@Slf4j
public class MonitoringController {

    private final RequestCounterService requestCounterService;

    @GetMapping("/request-count")
    public int getRequestCount() {
        log.info("Request received for request count");
        int count = requestCounterService.getCount();
        log.debug("Current request count: {}", count);
        return count;
    }
}