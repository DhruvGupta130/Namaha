package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.model.ServiceArea;
import com.trulydesignfirm.namaha.repository.ServiceAreaRepository;
import com.trulydesignfirm.namaha.service.ServiceAreaService;
import com.trulydesignfirm.namaha.util.GeoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceAreaServiceImpl implements ServiceAreaService {

    private final ServiceAreaRepository repository;

    private static final double GLOBAL_RADIUS_KM = 50.0; // global radius for all service areas

    @Override
    public boolean isDeliverable(double userLat, double userLon) {
        // 1. calculate bounding box around the user
        double[] box = GeoUtils.boundingBox(userLat, userLon, GLOBAL_RADIUS_KM);

        // 2. fetch candidate areas from DB
        List<ServiceArea> candidates = repository.findActiveWithinBoundingBox(
                box[0], box[1], box[2], box[3]
        );

        // 3. check distance using the global radius
        return candidates.stream()
                .anyMatch(area -> GeoUtils.distanceKm(userLat, userLon, area.getLatitude(), area.getLongitude()) <= GLOBAL_RADIUS_KM);
    }
}