package com.trinhkimlong.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "_order_line_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private Long price;
    private Integer quantity;
}
