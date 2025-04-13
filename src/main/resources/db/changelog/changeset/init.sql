--liquibase formatted sql
--changeset liquibase:1
--database: postgresql

drop schema if exists shop cascade;

create schema if not exists shop;

create table if not exists shop.items
(
    id          serial primary key not null,
    title       text               not null,
    price       numeric            not null,
    description varchar            not null,
    img_path    varchar            not null
);

create table if not exists shop.orders
(
    id        serial primary key not null,
    status    varchar            not null,
    completed timestamp          not null default now(),
    created   timestamp          not null default now()
);

create table if not exists shop.orders_items
(
    id       serial primary key not null,
    order_id integer            not null,
    constraint orders_items_orders_fkey foreign key (order_id)
        references shop.orders (id) match simple
        on update cascade on delete cascade,
    item_id  integer            not null,
    constraint orders_items_items_fkey foreign key (item_id)
        references shop.items (id) match simple
        on update cascade on delete cascade,
    count    smallint           not null default 0
);