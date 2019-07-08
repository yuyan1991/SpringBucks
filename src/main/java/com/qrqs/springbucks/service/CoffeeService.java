package com.qrqs.springbucks.service;

import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.repositories.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class CoffeeService {
    private static final String CACHE = "springbucks-coffee";

    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> findAll() {
        List<Coffee> coffeeList = coffeeRepository.findAll();
        log.info("All Coffee is {}", coffeeList);

        return coffeeList;
    }

    public Optional<Coffee> findOneCoffee(String name) {
        Optional<Coffee> coffee = null;

        HashOperations<String, String, Coffee> coffeeCache = redisTemplate.opsForHash();
        if (redisTemplate.hasKey(CACHE) && coffeeCache.hasKey(CACHE, name)) {
            coffee = Optional.of(coffeeCache.get(CACHE, name));
            log.info("Get Coffee {} from cache :: ", name, coffee);
        } else {
            ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", exact().ignoreCase());
            coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));

            printCoffeeInfo(coffee);

            if (coffee.isPresent()) {
                coffeeCache.put(CACHE, name, coffee.get());
                redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES);
            }
        }

        return coffee;
    }

    public Optional<Coffee> findByName(String name) {
        Optional<Coffee> coffee = coffeeRepository.findByName(name);

        printCoffeeInfo(coffee);

        return coffee;
    }

    private void printCoffeeInfo(Optional<Coffee> coffee) {
        if (coffee.isPresent()) {
            log.info("Coffee found :: {}", coffee.get());
        } else {
            log.info("No coffee found!");
        }
    }
}
