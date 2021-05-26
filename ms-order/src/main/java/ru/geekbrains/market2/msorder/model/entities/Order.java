package ru.geekbrains.market2.msorder.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "id")
    private List<OrderItem> items;

    @Column(name = "total_price")
    private BigDecimal price;

    @Column(name = "address")
    private String address;
    
    @Column(name = "customer_id")
    private Long userId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Order(Cart cart, Long userId, String address) {
        this.items = new ArrayList<>();
        this.userId = userId;
        this.address = address;
        this.price = cart.getPrice();
        for (CartItem ci : cart.getItems()) {
            OrderItem oi = new OrderItem(ci);
            oi.setOrder(this);
            this.items.add(oi);
        }
    }
}
