package edu.jespinoza.basic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public interface TransformService<T, R> {
    Function<T, R> getFunction();

    Function<R, T> getFunctionDTO();

    default R transform(T t) {
        return getFunction().apply(t);
    }

    default T transformDTO(R r) {
        return getFunctionDTO().apply(r);
    }

    default Map<String, Object> transform(int size, Collection<T> p) {
        Collection<R> collection = p.stream().map(getFunction()).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("data", collection);
        map.put("page", size);
        map.put("per_page", collection.size());

        return map;
    }
}
