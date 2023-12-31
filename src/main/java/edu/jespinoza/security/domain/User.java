package edu.jespinoza.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private long id;
    private String userName;
    private String password;
    private String email;
    private short active;
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id || userName.equals(user.getUserName()) || email.equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}