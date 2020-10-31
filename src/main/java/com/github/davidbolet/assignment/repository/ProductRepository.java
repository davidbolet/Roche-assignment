package com.github.davidbolet.assignment.repository;

import com.github.davidbolet.assignment.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends CrudRepository<Product, String> {

}
