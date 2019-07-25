package com.qrqs.springbucks.entry;

import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.model.OrderState;
import com.qrqs.springbucks.database.model.Orders;
import com.qrqs.springbucks.database.service.CoffeeService;
import com.qrqs.springbucks.database.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
@Slf4j
public class SpringbucksRunner implements ApplicationRunner {
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private OrdersService ordersService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        coffeeService.initCache()
                    .then(
                            coffeeService.findOneCoffee("mocha")
                                        .flatMap(c -> {
                                            Orders order = createOrder("Ziqi", c);
                                            return ordersService.create(order);
                                        })
                                        .doOnError(t -> log.error("error", t))
                    )
                    .subscribe(o -> log.info("Create Order :: {}", o));
        log.info("After Subscribe");
        Thread.sleep(5000);
    }

    private Orders createOrder(String customer, Coffee... coffees) {
        return Orders.builder()
                .customer(customer)
                .items(Arrays.asList(coffees))
                .state(OrderState.INIT)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
    }
}
