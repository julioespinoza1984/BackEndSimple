package edu.jespinoza.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "roles",
        "FECHA_CREACION",
        "FECHA_MODIFICA",
        "OPER_CREADOR",
        "OPER_MODIFICA",
        "ESTADO_REGISTRO",
        "ID_USEX",
        "NOMBRE",
        "CORREO",
        "NOMBRE_DE_USUARIO",
        "TOKEN",
        "SUCURSAL",
        "IDENTIFICACION",
        "COMPANIA",
        "VALIDO_HASTA"
})
public class UserResponse {
    @JsonProperty("roles")
    private List<Object> roles = null;
    @JsonProperty("FECHA_CREACION")
    private Object fechaCreacion;
    @JsonProperty("FECHA_MODIFICA")
    private Object fechaModifica;
    @JsonProperty("OPER_CREADOR")
    private Object operCreador;
    @JsonProperty("OPER_MODIFICA")
    private Object operModifica;
    @JsonProperty("ESTADO_REGISTRO")
    private String estadoRegistro;
    @JsonProperty("ID_USEX")
    private Object idUsex;
    @JsonProperty("NOMBRE")
    private String nombre;
    @JsonProperty("CORREO")
    private String correo;
    @JsonProperty("NOMBRE_DE_USUARIO")
    private String nombreDeUsuario;
    @JsonProperty("TOKEN")
    private Object token;
    @JsonProperty("SUCURSAL")
    private Object sucursal;
    @JsonProperty("IDENTIFICACION")
    private Object identificacion;
    @JsonProperty("COMPANIA")
    private Object compania;
    @JsonProperty("VALIDO_HASTA")
    private Integer validoHasta;
    @JsonProperty("USUARIO_DOMINIO")
    private String usuarioDominio;
}