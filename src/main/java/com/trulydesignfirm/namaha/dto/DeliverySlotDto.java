package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.DeliverySlot;

import java.time.LocalTime;

public record DeliverySlotDto(
        String name,
        LocalTime startTime,
        LocalTime endTime
) {
    public DeliverySlotDto(DeliverySlot slot) {
        this(slot.name(), slot.getStart(), slot.getEnd());
    }
}