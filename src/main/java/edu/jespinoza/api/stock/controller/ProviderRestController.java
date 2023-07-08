package edu.jespinoza.api.stock.controller;

import edu.jespinoza.api.stock.domain.Provider;
import edu.jespinoza.api.stock.dto.ProviderDTO;
import edu.jespinoza.basic.ActivateObjectService;
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

@RestController("providerRestController")
@RequestMapping(path = "/provider")
@CrossOrigin(origins = "*")
@Slf4j
public class ProviderRestController {
    @Autowired
    @Qualifier("providerService")
    private ActivateObjectService<Provider> service;

    @Autowired
    @Qualifier("providerTransformService")
    private TransformService<Provider, ProviderDTO> transformService;

    @GetMapping(value = "/getPage", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getProviderPage(@RequestParam(required = false) String title,
                                                           @RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "5") int size)
            throws ApplicationException, TechnicalException {
        Collection<Provider> pageProvider = service.getPage(title, page, size);
        int sizeCollection = service.count();
        int totalPages = (int) Math.ceil((double) sizeCollection/(double) page);
        Map<String, Object> response = transformService.transform(size, pageProvider);
        response.put("total", sizeCollection);
        response.put("total_pages", totalPages);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getById/{id}", produces = "application/json")
    public ResponseEntity<ProviderDTO> getById(@PathVariable("id") long id)
            throws ApplicationException, TechnicalException {
        Provider provider = service.getById(id);
        ProviderDTO providerDTO = transformService.transform(provider);
        return ResponseEntity.ok(providerDTO);
    }

    @DeleteMapping(value = "/deactivate/{id}", produces = "application/json")
    public ResponseEntity<CustomMessage> deactivate(@PathVariable("id") long id)
            throws ApplicationException, TechnicalException {
        service.activate(id, false);
        String message = "Desactivo Proveedor " + id;
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }

    @DeleteMapping(value = "/activate/{id}", produces = "application/json")
    public ResponseEntity<CustomMessage> activate(@PathVariable("id") long id)
            throws ApplicationException, TechnicalException {
        service.activate(id,  true);
        String message = "Activo Proveedor " + id;
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<CustomMessage> update(@RequestBody ProviderDTO providerDTO)
            throws ApplicationException, TechnicalException {
        Provider provider = transformService.transformDTO(providerDTO);
        service.update(provider);
        String message = "Proveedor " + provider.getName() + " Actualizado con Éxito";
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }

    @PostMapping(value = "/save", produces = "application/json")
    public ResponseEntity<CustomMessage> save(@RequestBody ProviderDTO providerDTO)
            throws ApplicationException, TechnicalException {
        Provider provider = transformService.transformDTO(providerDTO);
        service.save(provider);
        String message = "Proveedor " + provider.getName() + " Guardado con Éxito";
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }
}
