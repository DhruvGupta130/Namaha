package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.ServiceAreaDto;
import com.trulydesignfirm.namaha.exception.ServiceAreaException;
import com.trulydesignfirm.namaha.model.ServiceArea;
import com.trulydesignfirm.namaha.repository.ServiceAreaRepo;
import com.trulydesignfirm.namaha.service.ServiceAreaService;
import com.trulydesignfirm.namaha.util.GeoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceAreaServiceImpl implements ServiceAreaService {

    private static final double GLOBAL_RADIUS_KM = 20.0; // global radius for all service areas
    private final ServiceAreaRepo serviceAreaRepo;

    @Override
    public Response getServiceAreas(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ServiceAreaDto> serviceAreaDtos = serviceAreaRepo
                .findAll(pageable)
                .map(ServiceAreaDto::new);
        return new Response("All Service Areas fetched successfully!", HttpStatus.OK, serviceAreaDtos);
    }

    @Override
    public Response createServiceArea(ServiceAreaDto request) {
        ServiceArea serviceArea = new ServiceArea();
        addServiceArea(request, serviceArea);
        serviceAreaRepo.save(serviceArea);
        return new Response("Service Area created successfully!", HttpStatus.CREATED, null);
    }

    @Override
    public Response updateServiceArea(Long id, ServiceAreaDto request) {
        ServiceArea serviceArea = serviceAreaRepo.findById(id)
                .orElseThrow(() -> new ServiceAreaException("Service Area not found!"));
        addServiceArea(request, serviceArea);
        serviceAreaRepo.save(serviceArea);
        return new Response("Service Area updated successfully!", HttpStatus.OK, null);
    }

    @Override
    public Response updateServiceAvailabilityStatus(Long id) {
        ServiceArea serviceArea = serviceAreaRepo.findById(id)
                .orElseThrow(() -> new ServiceAreaException("Service Area not found!"));
        serviceArea.setActive(!serviceArea.isActive());
        serviceAreaRepo.save(serviceArea);
        return new Response("Service Area status updated successfully!", HttpStatus.OK, null);
    }

    @Override
    public Response deleteServiceArea(Long id) {
        serviceAreaRepo.deleteById(id);
        return new Response("Service Area deleted successfully!", HttpStatus.OK, null);
    }

    @Override
    public boolean isDeliverable(double userLat, double userLon) {
        // 1. calculate bounding box around the user
        double[] box = GeoUtils.boundingBox(userLat, userLon, GLOBAL_RADIUS_KM);

        // 2. fetch candidate areas from DB
        List<ServiceArea> candidates = serviceAreaRepo.findActiveWithinBoundingBox(
                box[0], box[1], box[2], box[3]
        );

        // 3. check distance using the global radius
        return candidates.stream()
                .anyMatch(area -> GeoUtils.distanceKm(userLat, userLon, area.getLatitude(), area.getLongitude()) <= area.getRadiusKm());
    }

    private void addServiceArea(ServiceAreaDto request, ServiceArea serviceArea) {
        serviceArea.setName(request.name());
        serviceArea.setCity(request.city());
        serviceArea.setState(request.state());
        serviceArea.setPincode(request.pincode());
        serviceArea.setLatitude(request.latitude());
        serviceArea.setLongitude(request.longitude());
        serviceArea.setRadiusKm(request.radiusKm());
        serviceArea.setDeliveryCharge(request.deliveryCharge());
        serviceArea.setActive(request.active());
    }
}