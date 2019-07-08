package com.qrqs.springbucks.service;

import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.model.cache.CoffeeCache;
import com.qrqs.springbucks.database.repositories.CoffeeRepository;
import com.qrqs.springbucks.database.repositories.cache.CoffeeCacheRepository;
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
    @Autowired
    private CoffeeCacheRepository coffeeCacheRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> findAll() {
        List<Coffee> coffeeList = coffeeRepository.findAll();
        log.info("All Coffee is {}", coffeeList);

        return coffeeList;
    }

    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));

        printCoffeeInfo(coffee);

        return coffee;
    }

    public Optional<Coffee> findByName(String name) {
        Optional<Coffee> coffee = coffeeRepository.findByName(name);

        printCoffeeInfo(coffee);

        return coffee;
    }

    public Optional<Coffee> findCoffeeByCache(String name) {
        Optional<CoffeeCache> cachedCoffee = coffeeCacheRepository.findByName(name);
        Coffee result = null;
        if (cachedCoffee.isPresent()) {
            result = Coffee.builder().name(cachedCoffee.get().getName()).price(cachedCoffee.get().getPrice()).build();
        } else {
            Optional<Coffee> coffee = coffeeRepository.findByName(name);
            if (coffee.isPresent()) {
                result = coffee.get();
                CoffeeCache coffeeCache = CoffeeCache.builder().name(result.getName()).price(result.getPrice()).build();
                coffeeCacheRepository.save(coffeeCache);
            }
        }

        return Optional.of(result);
    }

    private void printCoffeeInfo(Optional<Coffee> coffee) {
        if (coffee.isPresent()) {
            log.info("Coffee found :: {}", coffee.get());
        } else {
            log.info("No coffee found!");
        }
    }
}
