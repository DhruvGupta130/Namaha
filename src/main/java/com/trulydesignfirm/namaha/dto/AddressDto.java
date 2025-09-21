package com.trulydesignfirm.namaha.dto;

import com.trulydesignfirm.namaha.constant.AddressType;
import com.trulydesignfirm.namaha.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressDto(

        Long id,

        @NotBlank(message = "House Number is required")
        String house,

        String area,

        String directions,

        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Country is required")
        String country,

        @NotBlank(message = "PinCode is required")
        @Pattern(regexp = "\\d{6}", message = "PinCode must be exactly 6 digits")
        String pinCode,

        @NotNull(message = "Address Type is required")
        AddressType addressType,

        @NotNull(message = "Latitude is required")
        Double latitude,

        @NotNull(message = "Longitude is required")
        Double longitude
) {
    public AddressDto(Address address) {
        this(
                address.getId(),
                address.getHouse(),
                address.getArea(),
                address.getDirections(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getPinCode(),
                address.getAddressType(),
                address.getLatitude(),
                address.getLongitude()
        );
    }
}