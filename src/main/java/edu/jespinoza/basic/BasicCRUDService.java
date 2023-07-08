package edu.jespinoza.basic;

import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.TechnicalException;

import java.util.Collection;

public interface BasicCRUDService<T> {
    void save(T t) throws ApplicationException, TechnicalException;

    void update(T t) throws ApplicationException, TechnicalException;

    T getById(long id) throws ApplicationException, TechnicalException;

    Collection<T> getPage(String filter, int page, int pageSize)
            throws ApplicationException, TechnicalException;

    int count();
}
