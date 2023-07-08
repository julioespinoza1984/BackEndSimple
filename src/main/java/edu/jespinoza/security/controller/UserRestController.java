package edu.jespinoza.security.controller;

import edu.jespinoza.api.stock.domain.Product;
import edu.jespinoza.basic.ActivateObjectService;
import edu.jespinoza.basic.TransformService;
import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.CustomMessage;
import edu.jespinoza.exception.TechnicalException;
import edu.jespinoza.security.domain.User;
import edu.jespinoza.security.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController("userRestController")
@RequestMapping(path = "/user")
@CrossOrigin(origins = "*")
@Slf4j
public class UserRestController {
    @Autowired
    @Qualifier("userService")
    private ActivateObjectService<User> service;

    @Autowired
    @Qualifier("userTransformService")
    private TransformService<User, UserDTO> transformService;

    @GetMapping(value = "/getPage", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getProvidePage(@RequestParam(required = false) String title,
                                                              @RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "5") int size)
            throws ApplicationException, TechnicalException {
        Collection<User> pageUser = service.getPage(title, page, size);
        int sizeCollection = service.count();
        int totalPages = (int) Math.ceil((double) sizeCollection/(double) page);
        Map<String, Object> response = transformService.transform(size, pageUser);
        response.put("total", sizeCollection);
        response.put("total_pages", totalPages);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getById/{id}", produces = "application/json")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") long id)
            throws ApplicationException, TechnicalException {
        User user = service.getById(id);
        UserDTO userDTO = transformService.transform(user);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping(value = "/deactivate/{id}", produces = "application/json")
    public ResponseEntity<CustomMessage> delete(@PathVariable("id") long id)
            throws ApplicationException, TechnicalException {
        service.activate(id, false);
        String message = "Desactivo Usuario " + id;
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }

    @DeleteMapping(value = "/activate/{id}", produces = "application/json")
    public ResponseEntity<CustomMessage> recovery(@PathVariable("id") long id)
            throws ApplicationException, TechnicalException {
        service.activate(id,  true);
        String message = "Activo Usuario " + id;
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<CustomMessage> update(@RequestBody UserDTO userDTO)
            throws ApplicationException, TechnicalException {
        User user = transformService.transformDTO(userDTO);
        service.update(user);
        String message = "Usuario " + user.getUserName() + " Actualizado con Éxito";
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }

    @PostMapping(value = "/save", produces = "application/json")
    public ResponseEntity<CustomMessage> save(@RequestBody UserDTO userDTO)
            throws ApplicationException, TechnicalException {
        User user = transformService.transformDTO(userDTO);
        service.save(user);
        String message = "Usuario " + user.getUserName() + " Guardado con Éxito";
        return ResponseEntity.ok(new CustomMessage(HttpStatus.OK.value(), message));
    }
}
