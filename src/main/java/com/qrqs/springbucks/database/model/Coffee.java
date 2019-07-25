package com.qrqs.springbucks.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qrqs.springbucks.database.serializer.MoneyDeserializer;
import com.qrqs.springbucks.database.serializer.MoneySerializer;
import lombok.*;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee implements Serializable {
    @Id
    private Long id;
    private String name;
    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money price;
    private Date createTime;
    private Date updateTime;
}
