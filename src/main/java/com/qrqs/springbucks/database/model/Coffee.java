package com.qrqs.springbucks.database.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
    private Long id;

    private String name;

    private Long price;
}
