package com.qrqs.springbucks.database.repository;

import com.qrqs.springbucks.database.model.Coffee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CoffeeRepository extends R2dbcRepository<Coffee, Long> {
    @Query("select * from coffee where name=$1")
    Mono<Coffee> findByName(String name);
}
