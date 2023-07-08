package edu.jespinoza.basic;

import edu.jespinoza.exception.ApplicationException;
import edu.jespinoza.exception.TechnicalException;

public interface ActivateObjectService<T> extends BasicCRUDService<T> {
    void activate(long id, boolean active) throws ApplicationException, TechnicalException;
}
