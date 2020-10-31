package com.github.davidbolet.assignment.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Order", schema = "public")
public class Order {
    @Id
    private Integer id;
    @Column
    private String buyerEmail;
    @Column
    private LocalDateTime placedTime;

    @ManyToMany(targetEntity = Product.class, cascade = { CascadeType.ALL })
    @JoinTable(name = "OrderProducts",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") })
    private List<Product> products;

    public Order() {
        products = new ArrayList<>();
        placedTime = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public LocalDateTime getPlacedTime() {
        return placedTime;
    }

    public void setPlacedTime(LocalDateTime placedTime) {
        this.placedTime = placedTime;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Transient
    @JsonGetter(value = "totalOrderAmount")
    public BigDecimal getTotalOrderPrice() {
        return products.stream().map(Product::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
