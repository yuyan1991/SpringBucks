package com.qrqs.springbucks.database.model;

import com.qrqs.springbucks.database.model.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Builder
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;
}
