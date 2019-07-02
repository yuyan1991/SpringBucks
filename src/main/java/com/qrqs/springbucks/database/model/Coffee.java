package com.qrqs.springbucks.database.model;

import lombok.*;
import org.joda.money.Money;

import java.util.Date;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
    private long id;
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
