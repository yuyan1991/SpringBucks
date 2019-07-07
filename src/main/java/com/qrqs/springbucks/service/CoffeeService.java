package com.qrqs.springbucks.service;

import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.repositories.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
@CacheConfig(cacheNames = "coffee")
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @CacheEvict(allEntries = true)
    public void reloadCoffee() {
    }

    @CacheEvict
    public void reloadCoffeeFindAll() {
    }

    @CacheEvict(key = "#name")
    public void reloadCoffeeFindByName(String name) {
    }

    @Cacheable
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

    @Cacheable
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
