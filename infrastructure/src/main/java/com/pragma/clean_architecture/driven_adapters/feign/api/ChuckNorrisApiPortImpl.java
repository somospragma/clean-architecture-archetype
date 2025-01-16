package com.pragma.clean_architecture.driven_adapters.feign.api;

import com.pragma.clean_architecture.driven_adapters.feign.dto.JokeDto;
import com.pragma.clean_architecture.driven_adapters.feign.mapper.JokeMapper;
import com.pragma.clean_architecture.model.joke.JokeModel;
import com.pragma.clean_architecture.model.joke.gateways.ChuckNorrisApiPort;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class ChuckNorrisApiPortImpl implements ChuckNorrisApiPort {
    private final ChuckNorrisApi chuckNorrisApi;
    private final JokeMapper jokeMapper;

    @Override
    @CircuitBreaker(name = "chuckNorrisService", fallbackMethod = "getRandomCategoryFallback")
    @RateLimiter(name = "chuckNorrisService")
    @Bulkhead(name = "chuckNorrisService")
    @Retry(name = "chuckNorrisService")
    public String getRandomCategory() {
        return chuckNorrisApi.getCategories().stream()
                .findAny()
                .orElse("dev");
    }

    @Override
    @CircuitBreaker(name = "chuckNorrisService", fallbackMethod = "getJokeFallback")
    @RateLimiter(name = "chuckNorrisService")
    @Bulkhead(name = "chuckNorrisService")
    @Retry(name = "chuckNorrisService")
    public JokeModel getJoke(String category) {
        JokeDto jokeDto = chuckNorrisApi.getJoke(category);
        return jokeMapper.toModel(jokeDto);
    }

    // Fallback methods
    public String getRandomCategoryFallback(Exception ex) {
        log.error("Fallback executed for getRandomCategory. Error: {}", ex.getMessage());
        return "dev"; // Default category
    }

    public JokeModel getJokeFallback(String category, Exception ex) {
        log.error("Fallback executed for getJoke. Category: {}. Error: {}", category, ex.getMessage());
        return new JokeModel("Temporarily unavailable", "dev"); // Default joke
    }
}