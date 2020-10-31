package com.github.davidbolet.assignment.domain;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


class OrderTest {

    @Test
    void testTotalOrderPriceIsCalculated() {
        Product p1 = new Product();
        p1.setPrice(BigDecimal.valueOf(100.5));
        Product p2 = new Product();
        p2.setPrice(BigDecimal.valueOf(137.5));
        Order o1 = new Order();
        o1.getProducts().add(p1);
        o1.getProducts().add(p2);

        Assert.assertEquals(BigDecimal.valueOf(238.0),o1.getTotalOrderPrice());
    }

    @Test
    void testTotalOrderPriceIsZeroForEmptyOrder() {
        Order o1 = new Order();
        Assert.assertEquals(BigDecimal.ZERO,o1.getTotalOrderPrice());
    }
}