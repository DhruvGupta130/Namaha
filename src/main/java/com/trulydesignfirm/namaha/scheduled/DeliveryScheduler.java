package com.trulydesignfirm.namaha.scheduled;

import com.trulydesignfirm.namaha.constant.SubscriptionStatus;
import com.trulydesignfirm.namaha.model.Delivery;
import com.trulydesignfirm.namaha.repository.DeliveryRepo;
import com.trulydesignfirm.namaha.repository.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryScheduler {

    private final SubscriptionRepo subscriptionRepo;
    private final DeliveryRepo deliveryRepo;

//    @Scheduled(cron = "0 0 2 * * ?")
    @Scheduled(fixedRate = 86400000)
    @Transactional
    public void createDailyDeliveries() {
        log.info("Creating daily deliveries on {}", LocalDate.now());
        List<Delivery> list = subscriptionRepo
                .findAllByStatus(SubscriptionStatus.ACTIVE)
                .stream()
                .map(Delivery::new)
                .toList();
        log.info("Creating {} deliveries from subscriptions on {}", list.size(), LocalDate.now());
        deliveryRepo.saveAll(list);
    }
}