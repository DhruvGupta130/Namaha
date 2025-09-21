package com.trulydesignfirm.namaha.scheduled;

import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.model.Delivery;
import com.trulydesignfirm.namaha.repository.DeliveryRepo;
import com.trulydesignfirm.namaha.repository.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryScheduler {

    private final SubscriptionRepo subscriptionRepo;
    private final DeliveryRepo deliveryRepo;

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void createDailyDeliveries() {
        List<Delivery> list = subscriptionRepo
                .findAllByStatus(SubscriptionStatus.ACTIVE)
                .stream()
                .map(Delivery::new)
                .toList();
        deliveryRepo.saveAll(list);
    }
}