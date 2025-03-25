package com.example.imdb.service;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RequestCounterService {
    private final AtomicInteger requestCount = new AtomicInteger(0);

    public void increment() {
        requestCount.incrementAndGet();
    }

    public int getCount() {
        return requestCount.get();
    }
}