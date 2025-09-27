package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.constant.DiscountType;
import com.trulydesignfirm.namaha.constant.OfferType;
import com.trulydesignfirm.namaha.dto.CustomerOfferDto;
import com.trulydesignfirm.namaha.dto.OfferCreateDto;
import com.trulydesignfirm.namaha.dto.OfferDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.exception.OfferException;
import com.trulydesignfirm.namaha.model.Offers;
import com.trulydesignfirm.namaha.model.Product;
import com.trulydesignfirm.namaha.repository.OffersRepo;
import com.trulydesignfirm.namaha.repository.ProductRepo;
import com.trulydesignfirm.namaha.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OffersServiceImpl implements OfferService {

    private final OffersRepo offersRepo;
    private final ProductRepo productRepo;

    /**
     * Validate an offer for a given order amount
     */
    @Override
    public BigDecimal applyOffer(String couponCode, BigDecimal orderAmount) {
        Offers offer = offersRepo.findByCouponCodeAndActiveTrue(couponCode.toUpperCase())
                .orElseThrow(() -> new OfferException("Invalid or inactive coupon"));
        return calculateDiscount(offer, orderAmount);
    }

    @Override
    public BigDecimal applyOffer(Offers offer, BigDecimal orderAmount) {
        if (offer == null) return BigDecimal.ZERO;
        return calculateDiscount(offer, orderAmount);
    }

    @Override
    public Response getAllOffers(int pageNumber, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<OfferDto> offers;
        if (keyword == null) offers = offersRepo.findAll(pageable).map(OfferDto::new);
        else offers = offersRepo
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable)
                .map(OfferDto::new);
        return new Response("Offers Fetched Successfully", HttpStatus.OK, offers);
    }

    @Override
    public Response createNewOffer(OfferCreateDto request) {
        Offers offer = new Offers();
        offer.setTitle(request.title());
        offer.setDescription(request.description());
        offer.setDiscount(request.discount());
        offer.setDiscountType(request.discountType());
        offer.setOfferType(request.offerType());
        offer.setCouponCode(request.couponCode().toUpperCase());
        offer.setMinOrderAmount(request.minOrderAmount());
        offer.setValidFrom(request.validFrom());
        offer.setValidUntil(request.validUntil());
        offer.setActive(true);
        offersRepo.save(offer);
        return new Response("Offer created successfully", HttpStatus.CREATED, null);
    }

    @Override
    public Response updateOfferStatus(UUID offerId) {
        Offers offers = offersRepo.findById(offerId)
                .orElseThrow(() -> new OfferException("Offer not found"));
        if (offers.getValidUntil().isBefore(Instant.now())) throw new OfferException("Offer has expired");
        offers.setActive(!offers.isActive());
        offersRepo.save(offers);
        return new Response("Offer status updated successfully", HttpStatus.OK, null);
    }

    @Override
    public Response getAllEligibleOffers(int pageNumber, int pageSize, long productId, OfferType type) {
        Product product = productRepo
                .findById(productId)
                .orElseThrow(() -> new OfferException("Product not found"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        BigDecimal price = (type == OfferType.ONE_TIME) ? product.getOneTimePrice() : product.getSubscriptionPrice();
        Page<CustomerOfferDto> offers = offersRepo
                .findEligibleOffers(type, price, pageable)
                .map(CustomerOfferDto::new);
        return new Response("Offers Fetched Successfully", HttpStatus.OK, offers);
    }

    private BigDecimal calculateDiscount(Offers offer, BigDecimal orderAmount) {
        Instant now = Instant.now();
        if (offer.getValidFrom() != null && offer.getValidFrom().isAfter(now) ||
                offer.getValidUntil() != null && offer.getValidUntil().isBefore(now)) {
            throw new OfferException("Coupon is not valid at this time");
        }

        if (offer.getMinOrderAmount() != null && orderAmount.compareTo(offer.getMinOrderAmount()) < 0) {
            throw new OfferException("Minimum order amount not met for this coupon");
        }

        BigDecimal discount;
        if (offer.getDiscountType() == DiscountType.FLAT) {
            discount = offer.getDiscount();
        } else {
            discount = orderAmount.multiply(offer.getDiscount().divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
        }
        if (discount.compareTo(orderAmount) > 0) {
            discount = orderAmount;
        }
        return discount;
    }
}