package com.colombiavirus.www3.repositories;

import com.colombiavirus.www3.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    public Role findByNombre(String nombre);

}
