package com.colombiavirus.www3.services;

import com.colombiavirus.www3.models.Usuario;


public interface UsuarioServices {

    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
}
