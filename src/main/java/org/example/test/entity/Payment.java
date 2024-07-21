package org.example.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id ;
    @Column(name = "paymentId")
    private Integer paymentId;
    @Column(name = "name")
    private String name ;
    @Column(name = "companyName")
    private String companyName;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone ;
    @Column(name = "price")
    private Double price;

    @ToString.Exclude
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "creat_at")
    private LocalDateTime creatAt;
    @Column(name = "delivery")
    private LocalDate delivery;
    @Column(name = "received")
    private LocalDate received;

    @PrePersist
    public void prePersist(){
        this.creatAt = LocalDateTime.now();
    }

}
