# **Schema**

## **Menu**

```SQL
create table menu (
    id bigint not null,
    create_time timestamp,
    name varchar(255),
    price decimal(19,2),
    update_time timestamp,
    primary key (id)
)
```

## **Order**

```SQL
create table order (
    id bigint not null,
    create_time timestamp,
    customer varchar(255),
    state integer not null,
    update_time timestamp,
    primary key (id)
)
```

## **Order_Coffee**

```SQL
create table order_coffee (
   coffee_order_id bigint not null,
   items_id bigint not null
)
```