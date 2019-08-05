package com.qrqs.springbucks.controller;

import com.qrqs.springbucks.controller.request.NewOrderRequest;
import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.model.Orders;
import com.qrqs.springbucks.service.CoffeeService;
import com.qrqs.springbucks.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings({"unused"})
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable("id") Long id) {
        return ordersService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Orders create(@RequestBody NewOrderRequest request) {
        log.info("Receive new Order {}", request);
        Coffee[] coffeeList = coffeeService.getCoffeeByName(request.getItems()).toArray(new Coffee[] {});
        return ordersService.createOrder(request.getCustomer(), coffeeList);
    }
}
