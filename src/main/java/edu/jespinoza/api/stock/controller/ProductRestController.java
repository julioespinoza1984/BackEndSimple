package edu.jespinoza.api.stock.controller;

import edu.jespinoza.api.stock.domain.Product;
import edu.jespinoza.api.stock.dto.ProductDTO;
import edu.jespinoza.basic.BasicCRUDService;
import edu.jespinoza.basic.TransformService;
import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.CustomMessage;
import edu.jespinoza.exception.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController("productRestController")
@RequestMapping(path = "/product")
@CrossOrigin(origins = "*")
@Slf4j
public class ProductRestController {
    @Autowired
    @Qualifier("productService")
    private BasicCRUDService<Product> service;

    @Autowired
    @Qualifier("productTransformServiceImpl")
    private TransformService<Product, ProductDTO> transformService;

    @GetMapping(value = "/getPage", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getProvidePage(@RequestParam(required = false) String title,
                                                              @RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "5") int size)
            throws ApplicationException, TechnicalException {
        Collection<Product> pageProduct = service.getPage(title, page, size);
        int sizeCollection = service.count();
        int totalPages = (int) Math.ceil((double) sizeCollection/(double) page);
        Map<String, Object> response = transformService.transform(size, pageProduct);
        response.put("total", sizeCollection);
        response.put("total_pages", totalPages);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getById/{id}", produces = "application/json")
    public ResponseEntity<ProductDTO> getById(@PathVariable("id") long id)
            throws ApplicationException, TechnicalException {
        Product product = service.getById(id);
        ProductDTO productDTO = transformService.transform(product);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<CustomMessage> update(@RequestBody ProductDTO productDTO)
            throws ApplicationException, TechnicalException {
        Product product = transformService.transformDTO(productDTO);
        service.update(product);
        String message = "Producto " + product.getName() + " Actualizado con Éxito";
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }

    @PostMapping(value = "/save", produces = "application/json")
    public ResponseEntity<CustomMessage> save(@RequestBody ProductDTO productDTO)
            throws ApplicationException, TechnicalException {
        Product product = transformService.transformDTO(productDTO);
        service.save(product);
        String message = "Producto " + product.getName() + " Guardado con Éxito";
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }
}