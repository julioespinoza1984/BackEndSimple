package edu.jespinoza.api.stock.service.impl;

import edu.jespinoza.api.stock.domain.Provider;
import edu.jespinoza.api.stock.dto.ProviderDTO;
import edu.jespinoza.basic.TransformService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service("providerTransformService")
public class ProviderTransformServiceImpl implements TransformService<Provider, ProviderDTO> {

    @Override
    public Function<Provider, ProviderDTO> getFunction() {
        return p -> new ProviderDTO(p.getId(), p.getName(), p.getRif(), p.getPhone(),p.getActive() == 1);
    }

    @Override
    public Function<ProviderDTO, Provider> getFunctionDTO() {
        return p -> new Provider(p.getId(), p.getName(), p.getRif(), p.getPhone(), (short) (p.isActive() ? 1 : 0));
    }
}
