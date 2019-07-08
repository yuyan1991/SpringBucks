package com.qrqs.springbucks.database.model.cache;

import lombok.*;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "springbucks-coffee", timeToLive = 60)
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeCache {
    @Id
    private Long id;
    @Indexed
    private String name;
    private Money price;
}
