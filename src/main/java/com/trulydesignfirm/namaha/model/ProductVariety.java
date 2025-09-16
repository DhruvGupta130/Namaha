package com.trulydesignfirm.namaha.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductVariety {

    @Id
    private String name;

    public ProductVariety(String name) {
        this.name = name.toUpperCase();
    }
}
