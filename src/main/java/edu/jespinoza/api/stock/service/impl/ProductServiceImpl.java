package edu.jespinoza.api.stock.service.impl;

import edu.jespinoza.api.stock.dao.ProductDao;
import edu.jespinoza.api.stock.domain.Product;
import edu.jespinoza.basic.BasicCRUDService;
import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("productService")
@Slf4j
public class ProductServiceImpl implements BasicCRUDService<Product> {
    @Autowired
    private ProductDao dao;

    @Override
    public void save(Product product) throws ApplicationException, TechnicalException {
        if(product == null) {
            throw  new ApplicationException("Producto no puede ser nulo");
        }
        if(product.getCode() == null) {
            throw  new ApplicationException("El Código del Producto no puede ser nulo");
        }
        if(product.getCode().isEmpty()) {
            throw  new ApplicationException("El Código del Producto no puede estar vacío");
        }
        dao.save(product);
    }

    @Override
    public void update(Product product) throws ApplicationException, TechnicalException {
        if(product == null) {
            throw  new ApplicationException("Producto no puede ser nulo");
        }
        if(product.getCode() == null) {
            throw  new ApplicationException("El Código del Producto no puede ser nulo");
        }
        if(product.getCode().isEmpty()) {
            throw  new ApplicationException("El Código del Producto no puede estar vacío");
        }
        Product objectInDB = getById(product.getId());
        objectInDB.setName(product.getName());
        objectInDB.setDescription(product.getDescription());
        objectInDB.setPrice(product.getPrice());
    }

    @Override
    public Product getById(long id) throws ApplicationException, TechnicalException {
        Optional<Product> optional;
        try {
            optional = dao.findById(id);
        } catch (Exception e) {
            throw new TechnicalException(e.getMessage(), e);
        }
        return optional.orElseThrow(() -> new ApplicationException("Producto " + id + " no existe en BD"));
    }

    @Override
    public Collection<Product> getPage(String filter, int page, int pageSize) throws ApplicationException, TechnicalException {
        if (page <= 0) {
            page = 1;
        }
        Collection<Product> collection;
        try {
            if (filter == null || filter.isEmpty()) {
                collection = dao.findAll(page, pageSize);
            } else {
                collection = dao.loadByCode(filter, page, pageSize);
            }
            return collection.stream().sorted(Comparator.comparing(Product::getCode)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException(e.getMessage(), e);
        }
    }

    @Override
    public int count() {
        return dao.count();
    }
}