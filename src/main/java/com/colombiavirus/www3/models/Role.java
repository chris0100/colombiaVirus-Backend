package com.colombiavirus.www3.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20)
    private String nombre;

    //Si se quisiera utilizar de forma bidireccional para obtener la lista de usuarios a partir de los roles se hace
    //@ManyToMany(mappedBy = "listaRoles")
    //private List<Usuario> listaUsuarios;

    //GETTER AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static final long serialVersionUID = 1L;
}

