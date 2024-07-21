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
@Table(name = "favorites")
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.DETACH})
    @JoinTable(name = "favorites_products",
            joinColumns = @JoinColumn(name = "favorites_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Product> products = new LinkedHashSet<>();

    @Column(name = "status")
    private Boolean status;

    @PrePersist
    public void prePersist(){
        if(this.products.isEmpty()){
            this.status = false;
        }else {
            this.status = true ;
        }

    }
    @PreRemove
    void PreRemove(){
        this.setUser(null);
        for (Product p : products){
            p = null;
        }
    }

}