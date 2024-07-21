package org.example.test.Dto.projection;

import lombok.RequiredArgsConstructor;
import org.example.test.entity.Category;
import org.example.test.entity.Discount;
import org.example.test.entity.Product;
import org.example.test.entity.Rate;

import java.util.List;

public interface ProductInfo {
    Integer getId();
    String getName();
    String getDescription();
    Category getCategory();
    Discount getDiscount();
    String getThumbnail();
    List<String> getListImage();
    boolean getStatus();
    Double getPrice();
    Rate getRate();

    @RequiredArgsConstructor
    class ProductInfoImpl implements ProductInfo{
        private final Product product;
        @Override
        public Integer getId() {
            return product.getId();
        }

        @Override
        public String getName() {
            return product.getName();
        }

        @Override
        public String getDescription() {
            return product.getDescription();
        }

        @Override
        public Category getCategory() {
            return product.getCategory();
        }

        @Override
        public Discount getDiscount() {
            return product.getDiscount();
        }

        @Override
        public String getThumbnail() {
            return product.getThumbnail();
        }

        @Override
        public List<String> getListImage() {
            return product.getListImage();
        }

        @Override
        public boolean getStatus() {
            return false;
        }

        @Override
        public Double getPrice() {
            return product.getPrice();
        }

        @Override
        public Rate getRate() {
            return product.getRate();
        }
        static ProductInfo of(Product product) {
            return new ProductInfoImpl(product);
        }
    }
}
