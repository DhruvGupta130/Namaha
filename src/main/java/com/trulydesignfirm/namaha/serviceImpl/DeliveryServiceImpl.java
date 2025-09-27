package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.constant.DeliverySlot;
import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.dto.DeliveryDto;
import com.trulydesignfirm.namaha.dto.DeliveryInfoDto;
import com.trulydesignfirm.namaha.dto.DeliveryRequestDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.exception.ResourceNotFoundException;
import com.trulydesignfirm.namaha.exception.SubscriptionException;
import com.trulydesignfirm.namaha.exception.UserException;
import com.trulydesignfirm.namaha.model.Address;
import com.trulydesignfirm.namaha.model.Delivery;
import com.trulydesignfirm.namaha.model.LoginUser;
import com.trulydesignfirm.namaha.model.Product;
import com.trulydesignfirm.namaha.repository.AddressRepo;
import com.trulydesignfirm.namaha.repository.DeliveryRepo;
import com.trulydesignfirm.namaha.repository.LoginUserRepo;
import com.trulydesignfirm.namaha.repository.ProductRepo;
import com.trulydesignfirm.namaha.service.DeliveryService;
import com.trulydesignfirm.namaha.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepo deliveryRepo;
    private final LoginUserRepo loginUserRepo;
    private final AddressRepo addressRepo;
    private final ProductRepo productRepo;
    private final OfferService offerService;

    @Override
    public Response getAllPendingDeliveries(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<DeliveryInfoDto> delivery = deliveryRepo.findAllByStatus(DeliveryStatus.PENDING, pageable)
                .map(DeliveryInfoDto::new);
        return new Response("All Pending Deliveries fetched successfully", HttpStatus.ACCEPTED, delivery);
    }

    @Override
    public Response updateDeliveryStatus(UUID deliveryId, DeliveryStatus status) {
        Delivery delivery = deliveryRepo
                .findByIdAndStatus(deliveryId, DeliveryStatus.PENDING)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found!"));
        delivery.setStatus(status);
        deliveryRepo.save(delivery);
        return new Response("Delivery status updated successfully", HttpStatus.OK, null);
    }

    @Override
    public Response createNewDelivery(String mobile, DeliveryRequestDto request) {
        if (request.deliveryDate() == null) {
            throw new SubscriptionException("Delivery date is required");
        }

        if (!request.deliveryDate().isAfter(LocalDate.now())) {
            throw new SubscriptionException("Delivery date must be in the future");
        }

        LoginUser user = loginUserRepo
                .findByMobile(mobile)
                .orElseThrow(() -> new UserException("User not found!"));
        Address address = addressRepo
                .findByIdAndUser_MobileAndActiveTrue(request.addressId(), mobile)
                .orElseThrow(() -> new ResourceNotFoundException("No such Address Found!"));
        Product product = productRepo
                .findActiveOneTimeOnlyProductById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("No product available"));
        BigDecimal discount = offerService.applyOffer(request.couponCode(), product.getOneTimePrice());
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setSlot(request.slot());
        delivery.setUser(user);
        delivery.setAddress(address);
        delivery.setFinalPrice(product.getOneTimePrice().subtract(discount));
        delivery.setProduct(product);
        deliveryRepo.save(delivery);
        return new Response("Order Placed successfully", HttpStatus.CREATED, null);
    }

    @Override
    public Response getAllDeliveries(int pageNumber, int pageSize, DeliverySlot slot, DeliveryStatus status, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Delivery> deliveryPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            deliveryPage = deliveryRepo.findByProduct_titleContainingIgnoreCaseOrUser_nameContainingIgnoreCase(keyword, keyword, pageable);
        } else if (slot != null && status != null) {
            deliveryPage = deliveryRepo.findBySlotAndStatus(slot, status, pageable);
        } else if (slot != null) {
            deliveryPage = deliveryRepo.findBySlot(slot, pageable);
        } else if (status != null) {
            deliveryPage = deliveryRepo.findByStatus(status, pageable);
        } else deliveryPage = deliveryRepo.findAll(pageable);
        return new Response("All Deliveries fetched Successfully!", HttpStatus.OK, deliveryPage.map(DeliveryInfoDto::new));
    }

    @Override
    public Response getUserDeliveries(String mobile, int pageNumber, int pageSize, DeliveryStatus status) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<DeliveryDto> page = (status != null) ? deliveryRepo
                .findAllByUser_MobileAndStatus(mobile, status, pageable)
                .map(DeliveryDto::new) : deliveryRepo
                .findAllByUser_Mobile(mobile, pageable)
                .map(DeliveryDto::new);
        return new Response("Deliveries fetched successfully", HttpStatus.ACCEPTED, page);
    }
}
