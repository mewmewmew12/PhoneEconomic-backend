package org.example.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name ="name")
    private String name;
    @Column(name ="price")
    private Double price;
    @Column(name ="thumbnail")
    private String thumbnail;
    @Type(JsonType.class) // đánh giấu kiểu List dưới dạng Jsson
    @Column(name ="listImage", columnDefinition = "json")
    private List<String> ListImage;
    @Column(name = "content")
    private String content;
    @Column(name = "detail")
    private String detail;
    @Column(name = "description")
    private String description;
    @Column(name = "nums") // số lượng spham ban đầu
    private Integer nums;
    @Column(name = "numsSold")
    private Integer numsSold; // số sp bán ra
    @Column(name = "view")
    private Integer view;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "createAt")
    private LocalDateTime createAt;
    @Column(name = "sales")
    private Double sales;


    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "discount_id")
    private Discount discount;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;

    @PrePersist
    public void prePersist(){
        this.createAt = LocalDateTime.now();
        this.view = 1 ;
        this.numsSold = 0;
        this.sales = 0.0 ;
        if(numsSold == nums){ // đã bán thế
            this.status = false ;
        }
        if(this.discount != null){
            Double numbers = (double)(this.getPrice() *(100.0 - discount.getPercent()))/100;
            double roundedNumber = Math.round(numbers * 100.0) / 100.0;
            this.setSales(roundedNumber);
        }
        this.status = true;
    }
    @Modifying
    @PreUpdate
    void PreUpdate(){
        if(this.thumbnail.isEmpty()){
            this.thumbnail = this.ListImage.get(0);
        }
        this.sales = 0.0;
        if(this.discount != null){
            Double numbers = (double)(this.getPrice() *(100.0 - discount.getPercent()))/100;
            double roundedNumber = Math.round(numbers * 100.0) / 100.0;
            this.setSales(roundedNumber);
        }
        if(nums == 0){
            this.status = false;
        }
    }

    public Double getPrice() {
        return price == null ? 0D : price;
    }
}