package com.wish.whatsapp;

@SuppressWarnings("serial")
public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
