package com.qrqs.springbucks.database.repositories.cache;

import com.qrqs.springbucks.database.model.cache.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, Long> {
    Optional<CoffeeCache> findByName(String name);
}
