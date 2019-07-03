drop table coffee if exists;
drop table orders if exists;
drop table orders_coffee if exists;

create table coffee (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    name varchar(255),
    price bigint,
    primary key (id)
);

create table orders (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    customer varchar(255),
    state integer not null,
    primary key (id)
);

create table orders_coffee (
    orders_id bigint not null,
    items_id bigint not null
);
