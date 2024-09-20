package com.zerobase.storereservation.store.entity;

import com.zerobase.storereservation.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;

    private String storeLocation;

    private String storeDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
