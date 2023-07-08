package edu.jespinoza.api.stock.dao;

import edu.jespinoza.api.stock.domain.Product;

import java.util.Collection;
import java.util.Optional;

public interface ProductDao {
    Product findByCode(String code);

    Collection<Product> loadByCode(String code, int page, int pageSize);

    void save(Product product);

    Optional<Product> findById(long id);

    Collection<Product> findAll(int page, int pageSize);

    int count();

}
