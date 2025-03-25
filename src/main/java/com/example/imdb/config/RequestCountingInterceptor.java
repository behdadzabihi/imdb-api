package com.example.imdb.config;
import com.example.imdb.service.RequestCounterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestCountingInterceptor implements HandlerInterceptor {
    private final RequestCounterService requestCounterService;

    public RequestCountingInterceptor(RequestCounterService requestCounterService) {
        this.requestCounterService = requestCounterService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String path = request.getRequestURI();
        if (!path.equals("/api/imdb/request-count")) {
            requestCounterService.increment();
        }
        return true;
    }
}