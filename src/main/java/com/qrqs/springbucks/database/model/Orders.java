package com.qrqs.springbucks.database.model;

import com.qrqs.springbucks.datasource.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseEntity implements Serializable {
    private String customer;

    @Column(nullable = false)
    @Enumerated
    private OrderState state;

    @ManyToMany
    @JoinTable(name = "orders_coffee")
    @OrderBy("id")
    private List<Coffee> items;

    public enum OrderState {
        INIT, PAID, BREWING, BREWED, TAKEN, CANCELLED
    }
}
