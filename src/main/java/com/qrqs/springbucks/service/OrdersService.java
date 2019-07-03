package com.qrqs.springbucks.service;

import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.model.Orders;
import com.qrqs.springbucks.database.model.state.OrderState;
import com.qrqs.springbucks.database.repositories.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.qrqs.springbucks.database.model.state.OrderState.INIT;

@Service
@Transactional
@Slf4j
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Transactional
    public Orders createOrder(String customer, Coffee... coffeeOrderList) {
        Orders order = Orders.builder()
                            .customer(customer)
                            .state(INIT)
                            .items(Arrays.asList(coffeeOrderList))
                            .build();
        ordersRepository.save(order);
        log.info("New Order :: {}", order);

        return order;
    }

    @Transactional
    public boolean updateState(Orders order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.error("Must follow state flow :: {} -> {}", state, order.getState());
            return false;
        }

        order.setState(state);
        ordersRepository.save(order);
        log.info("Updated order is {}", order);

        return true;
    }
}
