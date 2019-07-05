package com.qrqs.springbucks.database.model;

import lombok.*;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Coffee {
    @Id
    private String id;

    private String name;

    private Money price;
    
    private Date createTime;

    private Date updateTime;
}
