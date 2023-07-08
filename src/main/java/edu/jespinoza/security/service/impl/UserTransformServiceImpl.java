package edu.jespinoza.security.service.impl;

import edu.jespinoza.basic.TransformService;
import edu.jespinoza.security.domain.User;
import edu.jespinoza.security.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service("userTransformService")
public class UserTransformServiceImpl implements TransformService<User, UserDTO> {
    public Function<User, UserDTO> getFunction() {
        return u -> new UserDTO(u.getId(), u.getUserName(), u.getPassword(), u.getEmail(),
                        u.getActive() == 1, u.getRole());
    }

    public Function<UserDTO, User> getFunctionDTO() {
        return u -> new User(u.getId(), u.getUserName(), u.getPassword(), u.getEmail(),
                        (short) (u.isActive() ? 1 : 0), u.getRole());
    }
}
