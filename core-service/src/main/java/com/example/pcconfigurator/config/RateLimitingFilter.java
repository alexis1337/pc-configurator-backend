package com.example.pcconfigurator.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
// Ограничение частоты запросов
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS_PER_MINUTE = 120;

    // Счётчик запросов для 1-го клиента
    private static class Counter {
        final AtomicInteger count = new AtomicInteger();
        volatile long windowStartMillis;
    }

    private final Map<String, Counter> counters = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        long now = Instant.now().toEpochMilli();

        // Получаем или создаём счётчик для текущего IP-адреса
        Counter counter = counters.computeIfAbsent(clientIp, ip -> {
            Counter c = new Counter();
            c.windowStartMillis = now;
            return c;
        });

        synchronized (counter) {
            long windowMs = 60_000; // временное окно (60 секунд)
            if (now - counter.windowStartMillis > windowMs) {
                counter.windowStartMillis = now;
                counter.count.set(0);
            }
            // Проверка превышения лимита
            if (counter.count.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
                response.setStatus(429);
                response.getWriter().write("Rate limit exceeded");
                return;
            }
        }

        // Передаём управление следующему фильтру в цепочке
        filterChain.doFilter(request, response);
    }
}

