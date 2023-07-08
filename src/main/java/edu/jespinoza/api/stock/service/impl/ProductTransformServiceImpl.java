package edu.jespinoza.api.stock.service.impl;

import edu.jespinoza.api.stock.domain.Product;
import edu.jespinoza.api.stock.dto.ProductDTO;
import edu.jespinoza.basic.TransformService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Function;

@Service("productTransformServiceImpl")
public class ProductTransformServiceImpl implements TransformService<Product, ProductDTO> {

    @Override
    public Function<Product, ProductDTO> getFunction() {
        return p -> new ProductDTO(p.getId(), p.getCode(), p.getName(), p.getDescription(),
                p.getPrice().doubleValue(), 0);
    }

    @Override
    public Function<ProductDTO, Product> getFunctionDTO() {
        return p -> new Product(p.getId(), p.getCode(), p.getName(), p.getDescription(), BigDecimal.valueOf(p.getPrice()));
    }
}
