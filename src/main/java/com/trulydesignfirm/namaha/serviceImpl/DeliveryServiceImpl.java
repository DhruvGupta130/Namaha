package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.constant.DeliveryStatus;
import com.trulydesignfirm.namaha.dto.DeliveryDto;
import com.trulydesignfirm.namaha.dto.DeliveryInfoDto;
import com.trulydesignfirm.namaha.dto.DeliveryRequestDto;
import com.trulydesignfirm.namaha.dto.Response;
import com.trulydesignfirm.namaha.exception.ResourceNotFoundException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepo deliveryRepo;
    private final LoginUserRepo loginUserRepo;
    private final AddressRepo addressRepo;
    private final ProductRepo productRepo;

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
        LoginUser user = loginUserRepo
                .findByMobile(mobile)
                .orElseThrow(() -> new UserException("User not found!"));
        Address address = addressRepo
                .findByIdAndUser_MobileAndActiveTrue(request.addressId(), mobile)
                .orElseThrow(() -> new ResourceNotFoundException("No such Address Found!"));
        Product product = productRepo
                .findActiveOneTimeOnlyProductById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("No product available"));
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setScheduledAt(request.deliveryDate()
                .atTime(request.slot().getEnd())
                .atZone(ZoneId.systemDefault())
                .toInstant());
        delivery.setUser(user);
        delivery.setAddress(address);
        delivery.setProduct(product);
        deliveryRepo.save(delivery);
        return new Response("Order Placed successfully", HttpStatus.CREATED, null);
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
