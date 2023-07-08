package edu.jespinoza.api.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Provider {
    private long id;
    private String name;
    private String rif;
    private String phone;
    private short active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return id == provider.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
