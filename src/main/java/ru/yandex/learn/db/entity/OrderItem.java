package ru.yandex.learn.db.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders_items",
        indexes = @Index(name = "uq_items_orders_item_id_order_id", columnList = "order, item", unique = true),
        schema = "shop")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Item item;

    @Column(columnDefinition = "INT DEFAULT '0'")
    private int count = 0;

    public OrderItem(@NonNull Order order, @NonNull Item item) {
        this.order = order;
        this.item = item;
        this.count = 1;
    }
}
