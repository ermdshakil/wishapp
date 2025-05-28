package com.wish.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class WishStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String email;
    private String phone;
    private String occasion;
    private String message;
    private int status; // 0: Pending, 1: Not Sent, 2: Sent
    private LocalDateTime createdAt;

    // Constructors
    public WishStatus(Long long1, String string, String string2, String occasion2, String message2, int i) {
    }

    public WishStatus(Long customerId, String email, String occasion, String message, int status) {
        this.customerId = customerId;
        this.email = email;
        this.occasion = occasion;
        this.message = message;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    // ...
}

