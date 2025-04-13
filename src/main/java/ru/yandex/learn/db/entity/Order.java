package ru.yandex.learn.db.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.learn.utils.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders", schema = "shop")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.CURRENT;

    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    private LocalDateTime created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    private LocalDateTime completed;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
}
