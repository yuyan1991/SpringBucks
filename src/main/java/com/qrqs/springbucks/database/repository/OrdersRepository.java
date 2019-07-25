package com.qrqs.springbucks.database.repository;

import com.qrqs.springbucks.database.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@Repository
public class OrdersRepository {
    @Autowired
    private DatabaseClient databaseClient;

    public Mono<Long> save(Orders orders) {
        return databaseClient.insert().into("orders")
                .value("customer", orders.getCustomer())
                .value("state", orders.getState().ordinal())
                .value("create_time", new Timestamp(orders.getCreateTime().getTime()))
                .value("update_time", new Timestamp(orders.getUpdateTime().getTime()))
                .fetch()
                .first()
                .flatMap(m -> Mono.just((Long) m.get("ID")))
                .flatMap(id -> Flux.fromIterable(orders.getItems())
                                    .flatMap(c -> databaseClient.insert().into("orders_coffee")
                                                    .value("orders_id", id)
                                                    .value("items_id", c.getId())
                                                    .then()).then(Mono.just(id)));
    }
}
