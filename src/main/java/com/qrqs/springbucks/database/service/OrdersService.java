package com.qrqs.springbucks.database.service;

import com.qrqs.springbucks.database.model.Orders;
import com.qrqs.springbucks.database.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    public Mono<Long> create(Orders order) {
        return ordersRepository.save(order);
    }
}
