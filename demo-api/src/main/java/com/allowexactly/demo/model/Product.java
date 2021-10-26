package com.allowexactly.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private String description;
    private String productId;
    private List<String> images;
    private String name;
    private List<Price> prices;
}
