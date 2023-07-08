package edu.jespinoza.api.stock.dao;

import edu.jespinoza.api.stock.domain.Provider;

import java.util.Collection;
import java.util.Optional;

public interface ProviderDao {
    Provider findByRif(String rif);

    Collection<Provider> loadByRif(String rif, int page, int pageSize);

    void save(Provider provider);

    Optional<Provider> findById(long id);

    Collection<Provider> findAll(int page, int pageSize);

    int count();
}
