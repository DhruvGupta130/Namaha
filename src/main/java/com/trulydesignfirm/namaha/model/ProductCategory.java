package com.trulydesignfirm.namaha.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductCategory {

    @Id
    private String name;

    public ProductCategory(String name) {
        this.name = name.toUpperCase();
    }
}
