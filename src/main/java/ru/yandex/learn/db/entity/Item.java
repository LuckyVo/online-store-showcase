package ru.yandex.learn.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "items", schema = "shop")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String title;

    @NonNull
    @Column(nullable = false)
    private String description;

    @NonNull
    @Column(nullable = false)
    private String imgPath;

    @NonNull
    @Column(nullable = false)
    private Double price;

    @ToString.Exclude
    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();
}
