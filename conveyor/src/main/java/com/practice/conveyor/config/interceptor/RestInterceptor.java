package com.practice.conveyor.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class RestInterceptor implements HandlerInterceptor {

    private static final AtomicBoolean techWorkStatus = new AtomicBoolean(false);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (techWorkStatus.get()) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            try (var writer = response.getWriter()) {
                writer.print("Close for technical work");
            }
            return false;

        } else {
            return true;
        }
    }

    public boolean changeTechWorkStatus(Boolean status) {
        techWorkStatus.set(status);
        return status;
    }
}
