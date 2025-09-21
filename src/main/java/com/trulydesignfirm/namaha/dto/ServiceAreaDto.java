package com.trulydesignfirm.namaha.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trulydesignfirm.namaha.model.ServiceArea;
import jakarta.validation.constraints.*;

public record ServiceAreaDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank(message = "Service area name is required")
        String name,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Pincode is required")
        @Pattern(regexp = "\\d{6}", message = "Pincode must be a 6-digit number")
        String pincode,

        @NotNull(message = "Latitude is required")
        @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90.0")
        @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90.0")
        Double latitude,

        @NotNull(message = "Longitude is required")
        @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180.0")
        @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180.0")
        Double longitude,

        @NotNull(message = "Radius is required")
        @Positive(message = "Radius must be greater than 0")
        Double radiusKm,

        @PositiveOrZero(message = "Delivery charge must be 0 or greater")
        Double deliveryCharge,

        boolean active
) {
    public ServiceAreaDto(ServiceArea area) {
        this(
                area.getId(),
                area.getName(),
                area.getCity(),
                area.getState(),
                area.getPincode(),
                area.getLatitude(),
                area.getLongitude(),
                area.getRadiusKm(),
                area.getDeliveryCharge(),
                area.isActive()
        );
    }
}
