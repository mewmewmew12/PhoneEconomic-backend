package org.example.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
      Integer id ;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "totalPrice")
    private Double TotalPrice;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Set<OderItem> oderItems = new LinkedHashSet<>();

    public Double getTotalPrice() {
        if (this.oderItems != null) {
            return this.oderItems.stream()
                    .mapToDouble(OderItem::getPrice)
                    .sum();
        }
        return 0.0;
    }
    @PrePersist
    public void PrePersist(){
        this.TotalPrice = getTotalPrice();
//        if(this.oderItems != null ){
//            for (OderItem e : oderItems){
//                this.TotalPrice += (double) e.getPrice();
//            }
//        }
    }
}
