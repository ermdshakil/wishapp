package com.wish.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDTO {
    private Long customerId;
    private String name;
    private String email;
    private String eventDate;
    private String eventType;
    private int status;         // 0: Pending, 1: Not Sent, 2: Sent
    private String occasion;
    private String message;
}

