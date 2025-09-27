package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.dto.DeliveryRequestDto;
import com.trulydesignfirm.namaha.dto.Response;

import java.util.UUID;

public interface DeliveryService {
    Response getUserDeliveries(String mobile, int pageNumber, int pageSize, DeliveryStatus status);

    Response getAllPendingDeliveries(int pageNumber, int pageSize);

    Response updateDeliveryStatus(UUID deliveryId, DeliveryStatus status);

    Response createNewDelivery(String mobile, DeliveryRequestDto request);

    Response getAllDeliveries(int pageNumber, int pageSize, DeliverySlot slot, DeliveryStatus status, String keyword);
}
