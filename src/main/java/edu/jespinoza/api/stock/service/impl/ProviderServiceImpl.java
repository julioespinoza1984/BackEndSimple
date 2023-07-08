package edu.jespinoza.api.stock.service.impl;

import edu.jespinoza.api.stock.dao.ProviderDao;
import edu.jespinoza.api.stock.domain.Provider;
import edu.jespinoza.basic.ActivateObjectService;
import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("providerService")
@Slf4j
public class ProviderServiceImpl implements ActivateObjectService<Provider> {
    @Autowired
    private ProviderDao dao;

    @Override
    public void save(Provider provider) throws ApplicationException, TechnicalException {
        if(provider == null) {
            throw  new ApplicationException("Proveedor no puede ser nulo");
        }
        if(provider.getRif() == null) {
            throw  new ApplicationException("El Rif del Proveedor no puede ser nulo");
        }
        if(provider.getRif().isEmpty()) {
            throw  new ApplicationException("El Rif del Proveedor no puede estar vacío");
        }
        if(dao.findByRif(provider.getRif()) != null) {
            throw  new ApplicationException("Ya existe un Proveedor con este Rif: " + provider.getRif());
        }
        provider.setActive((short) 1);
        dao.save(provider);
    }

    @Override
    public void update(Provider provider) throws ApplicationException, TechnicalException {
        if(provider == null) {
            throw  new ApplicationException("Proveedor no puede ser nulo");
        }
        if(provider.getRif() == null) {
            throw  new ApplicationException("El Rif del Proveedor no puede ser nulo");
        }
        if(provider.getRif().isEmpty()) {
            throw  new ApplicationException("El Rif del Proveedor no puede estar vacío");
        }
        Provider objectInDB = getById(provider.getId());
        objectInDB.setName(provider.getName());
        objectInDB.setPhone(provider.getPhone());
    }

    @Override
    public void activate(long id, boolean active) throws ApplicationException, TechnicalException {
        Provider objectInDB = getById(id);
        objectInDB.setActive((short) (active ? 1 : 0));
    }

    @Override
    public Provider getById(long id) throws ApplicationException, TechnicalException {
        Optional<Provider> optional;
        try {
            optional = dao.findById(id);
        } catch (Exception e) {
            throw new TechnicalException(e.getMessage(), e);
        }
        return optional.orElseThrow(() -> new ApplicationException("Proveedot " + id + " no existe en BD"));
    }

    @Override
    public Collection<Provider>  getPage(String filter, int page, int pageSize) throws TechnicalException {
        if (page <= 0) {
            page = 1;
        }
        Collection<Provider> collection;
        try {
            if (filter == null || filter.isEmpty()) {
                collection = dao.findAll(page, pageSize);
            } else {
                collection =  dao.loadByRif(filter, page, pageSize);
            }
            return collection.stream().sorted(Comparator.comparing(Provider::getRif)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new TechnicalException(e.getMessage(), e);
        }
    }

    @Override
    public int count() {
        return dao.count();
    }
}
