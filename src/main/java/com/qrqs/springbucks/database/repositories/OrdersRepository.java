package com.qrqs.springbucks.database.repositories;

import com.qrqs.springbucks.database.model.Orders;
import com.qrqs.springbucks.database.repositories.base.BaseRepository;

import java.util.List;

public interface OrdersRepository extends BaseRepository<Orders, Long> {
    List<Orders> findByCustomerOrderByIdAsc(String customer);
    List<Orders> findByItemsName(String name);
}
