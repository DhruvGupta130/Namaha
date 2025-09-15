package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressDto(
        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Country is required")
        String country,

        @NotBlank(message = "Pincode is required")
        @Pattern(regexp = "\\d{6}", message = "Pincode must be exactly 6 digits")
        String pincode,

        @NotNull(message = "Latitude is required")
        Double latitude,

        @NotNull(message = "Longitude is required")
        Double longitude
) {
    public AddressDto(Address address) {
        this(address.getStreet(), address.getCity(), address.getState(), address.getCountry(), address.getPincode(), address.getLatitude(), address.getLongitude());
    }
}