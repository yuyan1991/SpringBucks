package com.qrqs.springbucks.database.model;

import com.qrqs.springbucks.database.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseEntity {
    @Column
    private String customer;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;

    @ManyToMany
    @JoinTable(name = "coffee_orders")
    private List<Coffee> items;

    public enum OrderState {
        INIT, PAID, BREWING, BREWED, TAKEN, CANCELLED
    }
}
