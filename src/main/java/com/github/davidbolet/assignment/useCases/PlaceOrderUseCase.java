package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Order;
import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.MissingRequiredFieldsException;
import com.github.davidbolet.assignment.exception.OrderAlreadyExistsException;
import com.github.davidbolet.assignment.exception.ProductNotFoundException;
import com.github.davidbolet.assignment.repository.OrderRepository;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class PlaceOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public PlaceOrderUseCase(OrderRepository repository, ProductRepository productRepository) {
        this.orderRepository = repository;
        this.productRepository = productRepository;
    }

    public Response execute(Request request) {
        checkRequiredFields(request.getOrder());
        if (orderRepository.existsById(request.getOrder().getId())) {
            throw new OrderAlreadyExistsException();
        }
        List<String> skuList = request.getOrder().getProducts().stream().map(x -> x.getSku()).collect(Collectors.toList());
        List<Product> retrievedProducts = new ArrayList<>();
        productRepository.findAllById(skuList).forEach(x ->retrievedProducts.add(x));
        Integer totalNonExisting = skuList.size() - retrievedProducts.size();
        if (totalNonExisting>0) {
            List<String> retrievedSkuList = retrievedProducts.stream().map(x->x.getSku()).collect(Collectors.toList());
            String firstNonExisting = skuList.stream().filter(x -> retrievedSkuList.contains(x)).findFirst().get();
            throw new ProductNotFoundException(String.format("%d product(s) from order %d doesn't exist, first non existing is %s",totalNonExisting, request.getOrder().getId(), firstNonExisting ));
        }
        Order toSave = request.getOrder();
        toSave.setProducts(retrievedProducts);
        return new Response(orderRepository.save(toSave));
    }

    private void checkRequiredFields(Order order) {
        if (isNull(order.getId())||isNull(order.getBuyerEmail())) {
            throw new MissingRequiredFieldsException();
        }
    }

    public static class Request {
        private final Order order;

        public Request(Order order) {
            this.order = order;
        }

        public Order getOrder() {
            return this.order;
        }
    }

    public static class Response {
        private final Order order;

        public Response(Order order) {
            this.order = order;
        }

        public Order getOrder() {
            return order;
        }
    }
}
