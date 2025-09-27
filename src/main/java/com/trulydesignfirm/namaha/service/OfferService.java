package com.trulydesignfirm.namaha.service;

import com.trulydesignfirm.namaha.constant.OfferType;
import com.trulydesignfirm.namaha.dto.OfferCreateDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.model.Offers;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public interface OfferService {
    BigDecimal applyOffer(String couponCode, BigDecimal orderAmount);

    BigDecimal applyOffer(Offers offer, BigDecimal orderAmount);

    Response getAllOffers(int pageNumber, int pageSize, String keyword);

    Response createNewOffer(@Valid OfferCreateDto request);

    Response updateOfferStatus(UUID offerId);

    Response getAllEligibleOffers(int pageNumber, int pageSize, long productId, OfferType type);
}
