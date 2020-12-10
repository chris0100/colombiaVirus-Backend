package com.colombiavirus.www3.controllers;


import com.colombiavirus.www3.models.Role;
import com.colombiavirus.www3.models.Usuario;
import com.colombiavirus.www3.repositories.RoleRepository;
import com.colombiavirus.www3.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UsuarioController {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepositoryObj;

    @Autowired
    private UsuarioService usuarioServiceObj;


    @PostMapping("/api/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addUser(@Valid @RequestBody Usuario usuario, BindingResult result) {

        Usuario usuarioNuevo = null;

        System.out.println(usuario.getRoles()[0]);

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            System.out.println("ha habido un error en la creacion");

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " - " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        // Se encripta la contraseña
        String password = bCryptPasswordEncoder.encode(usuario.getPassword());
        usuario.setPassword(password);

        // Se coloca el usuario como habilitado
        usuario.setEnabled(true);

        // Se leen los respectivos roles para que sean guardados en el usuario
        List<Role> listaRol = new ArrayList<>();
        for (String obj : usuario.getRoles()){
            Role roleEncontrado = roleRepositoryObj.findByNombre(obj);
            listaRol.add(roleEncontrado);
        }

        // Se cargan los roles en el usuario
        usuario.setListaRoles(listaRol);

        System.out.println("hasta aqui va el usuario: " + usuario);

        try {
            usuarioNuevo = usuarioServiceObj.save(usuario);
        } catch (DataAccessException e){
            System.out.println("ha habido un error tipo2");
            response.put("mensaje", "Error al realizar la creacion de usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido creado con exito");
        response.put("usuario", usuarioNuevo);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    // Locate all the pacients in DB
    @GetMapping("/api/usuarios/pacientes")
    public List<Usuario> listarUsuariosPacientes() {
        return usuarioServiceObj.listaPacientes("paciente");
    }


    // Locate all the Doctors in DB
    @GetMapping("/api/usuarios/doctores")
    public List<Usuario> listarUsuariosDoctores(){
        System.out.println("devuelve dr");
        return usuarioServiceObj.listaDoctores("doctor");
    }


    // Locate one pacient and doctor by id
    @GetMapping("/api/usuarios/pacdoc/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findPacientOrDoctor(@PathVariable Long id){
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();

        //Busca el cliente por el id en la BD
        try{
            usuario = usuarioServiceObj.findById(id);
        } catch (DataAccessException de){
            response.put("mensaje", "Error al realizar la consulta en la Base de Datos");
            response.put("error", de.getMessage() + " : " + de.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Si el usuario no aparece en la bd
        if(usuario == null){
            response.put("mensaje", "el usuario no se encuentra en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        //De lo contrario
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }


    // toggle activate a user by id
    @GetMapping("/api/usuarios/pacientes/toggle/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> toggleActivatePacient(@PathVariable Long id){
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();

        //Busca el cliente por el id en la BD
        try{
            usuario = usuarioServiceObj.findById(id);
        } catch (DataAccessException de){
            response.put("mensaje", "Error al realizar la consulta en la Base de Datos");
            response.put("error", de.getMessage() + " : " + de.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Si el usuario no aparece en la bd
        if(usuario == null){
            response.put("mensaje", "el usuario no se encuentra en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        //De lo contrario
        if(usuario.getEnabled()){
            usuario.setEnabled(false);
        }
        else {
            usuario.setEnabled(true);
        }

        Usuario usuarioModificado = usuarioServiceObj.save(usuario);
        return new ResponseEntity<Usuario>(usuarioModificado, HttpStatus.OK);
    }
}
