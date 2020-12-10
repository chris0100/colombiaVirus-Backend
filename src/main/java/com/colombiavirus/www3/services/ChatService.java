package com.colombiavirus.www3.services;

import com.colombiavirus.www3.models.Mensaje;

import java.util.List;

public interface ChatService {

    public List<Mensaje> obtenerUltimos10Mensajes();
    public Mensaje guardar(Mensaje mensaje);
}
