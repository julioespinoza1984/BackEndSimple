package edu.jespinoza.api.stock.dao.impl;

import edu.jespinoza.api.stock.dao.ProductDao;
import edu.jespinoza.api.stock.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository("productDao")
public class ProductDaoImpl implements ProductDao {
    // Using an in-memory Map
    // to store the object data
    private static final AtomicLong counter = new AtomicLong();
    private final Map<Long, Product> repository = new HashMap<>();


    @Override
    public Product findByCode(String code) {
        Collection<Product> collection =
                repository.values().stream().filter(p -> p.getCode().equals(code)).collect(Collectors.toList());
        if(collection.isEmpty()) {
            return null;
        }
        return collection.iterator().next();
    }

    @Override
    public Collection<Product> loadByCode(String code, int page, int pageSize) {
        List<Product> list =
                repository.values().stream().filter(p -> p.getCode().startsWith(code)).collect(Collectors.toList());
        int begin = pageSize * (page - 1);
        int end = pageSize * page;
        if(list.size() < end) {
            end = list.size();
        }
        return list.subList(begin, end);
    }

    @Override
    public void save(Product product) {
        product.setId(counter.incrementAndGet());
        repository.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Collection<Product> findAll(int page, int pageSize) {
        List<Product> list = new ArrayList<>(repository.values());
        int begin = pageSize * (page - 1);
        int end = pageSize * page;
        if(list.size() < end) {
            end = list.size();
        }
        return list.subList(begin, end);
    }

    @Override
    public int count() {
        return repository.size();
    }
}
