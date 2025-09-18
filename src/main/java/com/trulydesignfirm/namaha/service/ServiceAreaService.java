package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.dto.ServiceAreaDto;
import org.springframework.stereotype.Service;

@Service
public interface ServiceAreaService {
    Response getServiceAreas(int pageNumber, int pageSize);
    Response createServiceArea(ServiceAreaDto request);
    Response updateServiceArea(Long id, ServiceAreaDto request);
    Response updateServiceAvailabilityStatus(Long id);
    Response deleteServiceArea(Long id);
    boolean isDeliverable(double userLat, double userLon);
}