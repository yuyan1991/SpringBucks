package com.qrqs.springbucks.database.repositories;

import com.qrqs.springbucks.database.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Optional<Coffee> findByName(String name);
    Coffee getCoffeeByName(String name);
    List<Coffee> findByNameInOrderById(List<String> coffeeNameList);
}
