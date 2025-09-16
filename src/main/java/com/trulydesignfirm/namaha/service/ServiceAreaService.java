package com.trulydesignfirm.namaha.service;

import org.springframework.stereotype.Service;

@Service
public interface ServiceAreaService {
    boolean isDeliverable(double userLat, double userLon);
}