package com.colombiavirus.www3.services;

import com.colombiavirus.www3.models.Usuario;
import com.colombiavirus.www3.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsuarioService implements UsuarioServices, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepositoryObj;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositoryObj.findByEmail(email);

        if (usuario == null){
            System.out.println("Usuario no existe");
            throw new UsernameNotFoundException("Error: no existe el usuario");
        }


        //Se carga la lista de roles.
        List<GrantedAuthority> authorities = usuario.getListaRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(authority -> System.out.println("role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new User(usuario.getEmail(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioRepositoryObj.findByUsername(username);
    }


    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepositoryObj.findByEmail(email);
    }


    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepositoryObj.save(usuario);
    }


    @Override
    public List<Usuario> listaPacientes(String tipo) {
        return usuarioRepositoryObj.findAllByTipo(tipo);
    }


    @Override
    public List<Usuario> listaDoctores(String tipo) {
        return usuarioRepositoryObj.findAllByTipo(tipo);
    }


    @Override
    public Usuario findById(Long id) {
        return usuarioRepositoryObj.findById(id).orElse(null);
    }
}
