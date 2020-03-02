package com.qrqs.springbucks.controller;

import com.qrqs.springbucks.controller.request.NewCoffeeRequest;
import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.service.CoffeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused"})
@Controller
@RequestMapping("/coffee")
@Slf4j
@Api
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增咖啡")
    public Coffee addCoffee(@ApiParam(name = "新咖啡", value = "新品种咖啡", required = true)
                                @Valid NewCoffeeRequest newCoffee,
                            BindingResult result) {
        log.info("Enter into addCoffee() :: {}", newCoffee);
        if (result.hasErrors()) {
            log.warn("Binding Errors: {}", result);
            return null;
        }

        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

//    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.CREATED)
//    public Coffee addCoffee(@Valid NewCoffeeRequest newCoffee) {
//        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
//    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "批量新增咖啡")
    public List<Coffee> batchAddCoffee(@ApiParam(name="咖啡文件列表", value = "新增的咖啡列表清单文件", required = true)
                                           @RequestParam("file") MultipartFile file) {
        List<Coffee> coffees = new ArrayList<Coffee>();
        if (!file.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(
                                new InputStreamReader(file.getInputStream()))) {
                String str;
                while ((str = reader.readLine()) != null) {
                    String[] arr = StringUtils.split(str, " ");
                    if (arr != null && arr.length == 2) {
                        coffees.add(coffeeService.saveCoffee(arr[0], Money.of(CurrencyUnit.of("CNY"),
                                        NumberUtils.createBigDecimal(arr[1]))));
                    }
                }
            } catch (IOException ioe) {
                log.error("Exception", ioe);
            }
        }

        return coffees;
    }

    @GetMapping(params = "!name")
    @ResponseBody
    @ApiOperation(value = "查询所有咖啡")
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value = "根据id查询咖啡")
    public Coffee getById(@ApiParam(name = "id", value = "咖啡id编号", required = true) @PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffee(id);
        return coffee;
    }

    @GetMapping(value = "/", params = "name")
    @ResponseBody
    @ApiOperation("根据咖啡名查询咖啡")
    public Coffee getByName(@ApiParam(name = "咖啡名", value = "要查询的咖啡名字", required = true)
                                @RequestParam String name) {
        return coffeeService.getCoffee(name);
    }
}
