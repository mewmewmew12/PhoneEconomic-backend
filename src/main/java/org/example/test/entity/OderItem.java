package org.example.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "oder_item")
public class OderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nums")
    private Integer nums;
    @Column(name = "price")
    private Double price;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @PrePersist
    public void PrePersist() {
        if (product == null) {
            return;
        }
        this.price = (double) (this.product.getPrice() * nums);
        if (product.getDiscount() != null) {
            this.price = (double) (this.product.getSales() * nums);
        }
    }
}