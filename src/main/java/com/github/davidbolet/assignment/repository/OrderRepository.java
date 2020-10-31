package com.github.davidbolet.assignment.repository;

import com.github.davidbolet.assignment.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("orderRepository")
public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Query("Select o From Order o where o.placedTime>=:start and o.placedTime<=:end ")
    public List<Order> findOrdersBetweenDates(@Param(value="start") LocalDateTime startDate,
                                              @Param(value="end") LocalDateTime endDate);
}
