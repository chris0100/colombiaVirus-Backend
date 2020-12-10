package com.colombiavirus.www3.services;

import com.colombiavirus.www3.models.Usuario;

import java.util.List;


public interface UsuarioServices {

    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
    Usuario save(Usuario usuario);
    List<Usuario> listaPacientes(String tipo);
    List<Usuario> listaDoctores(String tipo);
    Usuario findById(Long id);
}
