# **Schema**

## **Coffee**

```SQL
create table coffee (
    id bigint not null,
    create_time timestamp,
    name varchar(255),
    price decimal(19,2),
    update_time timestamp,
    primary key (id)
)
```

## **Orders**

```SQL
create table orders (
    id bigint not null,
    create_time timestamp,
    customer varchar(255),
    state integer not null,
    update_time timestamp,
    primary key (id)
)
```

## **Orders_Coffee**

```SQL
create table orders_coffee (
   coffee_orders_id bigint not null,
   items_id bigint not null
)
```