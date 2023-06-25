package com.prnt.orderservice.dto;

public enum Logistic {
    HERMES(1), DHL(2);

    private final int value;
    Logistic(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
