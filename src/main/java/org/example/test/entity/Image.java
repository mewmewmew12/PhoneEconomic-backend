package org.example.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Lob
    @Column(name = "data" , columnDefinition = "LONGBLOB")
    private byte[] data;

    @Column(name = "type")
    private String type;

    @Column(name = "creatAt")
    private LocalDateTime creatAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist(){
        this.creatAt = LocalDateTime.now();
    }

}
