package com.allowexactly.demo.web;

import com.stripe.param.ProductListParams;

public class ProductService extends ParentService{

    public ProductService(String stripeKey){
        super(stripeKey);
    }

    public ProductListParams createProductLstParams(){
        return new ProductListParams.Builder()
                .setLimit(10L)
                .build();
    }
}
