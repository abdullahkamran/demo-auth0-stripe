package com.allowexactly.demo.web;

import com.allowexactly.demo.model.Price;
import com.allowexactly.demo.model.Product;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PriceCollection;
import com.stripe.model.ProductCollection;
import com.stripe.param.PriceListParams;
import com.stripe.param.ProductListParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Value("${stripe.keys.private}")
    private String secretStripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretStripeKey;
    }

    private ProductListParams createProductListParams() {
        return new ProductListParams.Builder()
                .setLimit(10L)
                .build();
    }

    private PriceListParams createPriceListParams(String productId) {
        return new PriceListParams.Builder()
                .setProduct(productId)
                .build();
    }

    private List<Price> getProductPrices(String productId) throws StripeException {
        List<Price> prices = new ArrayList<>();

        PriceCollection priceCollection = com.stripe.model.Price.list(createPriceListParams(productId));

        for (com.stripe.model.Price price : priceCollection.getData()) {
            StringBuilder amountBuilder = new StringBuilder(price.getUnitAmountDecimal().toString());
            amountBuilder.insert(amountBuilder.length() - 2, ".");
            prices.add(new Price(price.getId(), price.getCurrency(), amountBuilder.toString()));
        }

        return prices;
    }

    public List<Product> getAllProducts() throws StripeException {
        List<Product> products = new ArrayList<>();
        ProductCollection productCollection = com.stripe.model.Product.list(createProductListParams());

        for (com.stripe.model.Product product : productCollection.getData()) {
            products.add(new Product(product.getDescription(), product.getId(), product.getImages(), product.getName(), getProductPrices(product.getId())));
        }

        return products;
    }
}
