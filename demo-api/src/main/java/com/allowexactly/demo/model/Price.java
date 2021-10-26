package com.allowexactly.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Price {
    private String priceId;
    private String currency;
    private String unitAmount;
}
