package com.trulydesignfirm.namaha.constant;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public enum DeliverySlot {
    MORNING(LocalTime.of(5, 0), LocalTime.of(8, 0)),
    EVENING(LocalTime.of(5, 0), LocalTime.of(8, 0));

    private final LocalTime start;
    private final LocalTime end;

    DeliverySlot(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }
}