package com.github.davidbolet.assignment.repository;


import com.github.davidbolet.assignment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, String> {

}
