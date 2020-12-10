package com.colombiavirus.www3.repositories;

import com.colombiavirus.www3.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    //con jpa de se puede hacer paginacion y ordenamiento
    //crud consultas simples

    public Usuario findByUsername(String username);

    @Query("select u from Usuario u where u.username=?1")
    public Usuario findByUsername2(String username);

    Usuario findByEmail(String email);

    //Listar usuarios que sean pacientes o doctores
    public List<Usuario> findAllByTipo(String tipo);




}
