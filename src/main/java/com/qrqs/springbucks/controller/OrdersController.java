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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings({"unused"})
@Controller
@RequestMapping("/orders")
@Slf4j
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    @ResponseBody
    public Orders getOrder(@PathVariable("id") Long id) {
        return ordersService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Orders create(@RequestBody NewOrderRequest request) {
        log.info("Receive new Order {}", request);
        Coffee[] coffeeList = coffeeService.getCoffeeByName(request.getItems()).toArray(new Coffee[] {});
        return ordersService.createOrder(request.getCustomer(), coffeeList);
    }

    @ModelAttribute
    public List<Coffee> coffeeList() {
        return coffeeService.getAllCoffee();
    }

    @GetMapping
    public ModelAndView showCreateForm() {
        return new ModelAndView("create-order-form");
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createOrder(@Valid NewOrderRequest newOrderRequest, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            log.warn("Binding result :: {}", result);
            map.addAttribute("message", result.toString());
            return "create-order-form";
        }

        log.info("Receive new Order {}", newOrderRequest);
        Coffee[] coffeeList = coffeeService.getCoffeeByName(newOrderRequest.getItems()).toArray(new Coffee[] {});
        Orders order = ordersService.createOrder(newOrderRequest.getCustomer(), coffeeList);
        return "redirect:/orders/" + order.getId();
    }
}
