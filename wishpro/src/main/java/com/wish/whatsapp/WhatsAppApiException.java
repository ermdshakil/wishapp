package com.wish.whatsapp;

@SuppressWarnings("serial")
public class WhatsAppApiException extends RuntimeException {
    public WhatsAppApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
