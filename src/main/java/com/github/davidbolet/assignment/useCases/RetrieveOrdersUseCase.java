package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Order;
import com.github.davidbolet.assignment.exception.MissingRequiredFieldsException;
import com.github.davidbolet.assignment.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class RetrieveOrdersUseCase {
    private final OrderRepository orderRepository;

    public RetrieveOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public RetrieveOrdersUseCase.Response execute(RetrieveOrdersUseCase.Request request) {
        checkParams(request.getStartMoment(), request.getEndMoment());
        return new Response(orderRepository.findOrdersBetweenDates(request.getStartMoment(), request.getEndMoment()));
    }

    private void checkParams(LocalDateTime startMoment, LocalDateTime endMoment) {
        if (isNull(startMoment)||isNull(endMoment)||startMoment.isAfter(endMoment)) {
            throw new IllegalArgumentException();
        }
    }

    public static class Request {
        private final LocalDateTime startMoment;
        private final LocalDateTime endMoment;

        public Request(LocalDateTime startMoment, LocalDateTime endMoment) {
            this.startMoment = startMoment;
            this.endMoment = endMoment;
        }

        public LocalDateTime getStartMoment() {
            return startMoment;
        }

        public LocalDateTime getEndMoment() {
            return endMoment;
        }
    }

    public static class Response {
        private final List<Order> orders;

        public Response(List<Order> orders) {
            this.orders = orders;
        }

        public List<Order> getOrders() {
            return orders;
        }
    }
}
