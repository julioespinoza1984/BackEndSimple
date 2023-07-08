package edu.jespinoza.api.stock.dao.impl;

import edu.jespinoza.api.stock.dao.ProviderDao;
import edu.jespinoza.api.stock.domain.Provider;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository("providerDao")
public class ProviderDaoImpl implements ProviderDao {
    // Using an in-memory Map
    // to store the object data
    private static final AtomicLong counter = new AtomicLong();
    private final Map<Long, Provider> repository = new HashMap<>();

    @Override
    public Provider findByRif(String rif) {
        Collection<Provider> collection =
                repository.values().stream().filter(p -> p.getRif().equals(rif)).collect(Collectors.toList());
        if(collection.isEmpty()) {
            return null;
        }
        return collection.iterator().next();
    }

    @Override
    public Collection<Provider> loadByRif(String rif, int page, int pageSize) {
        List<Provider> list =
                repository.values().stream().filter(p -> p.getRif().startsWith(rif)).collect(Collectors.toList());
        int begin = pageSize * (page - 1);
        int end = pageSize * page;
        if(list.size() < end) {
            end = list.size();
        }
        return list.subList(begin, end);
    }

    @Override
    public void save(Provider provider) {
        provider.setId(counter.incrementAndGet());
        repository.put(provider.getId(), provider);
    }

    @Override
    public Optional<Provider> findById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Collection<Provider> findAll(int page, int pageSize) {
        List<Provider> list = new ArrayList<>(repository.values());
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
