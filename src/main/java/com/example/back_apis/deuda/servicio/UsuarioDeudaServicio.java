package com.example.back_apis.deuda.servicio;

import com.example.back_apis.deuda.repositorio.UsuarioDeudaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UsuarioDeudaServicio {

    @Autowired
    private UsuarioDeudaRepo usuarioDeudaRepo;

    public Flux<ResponseEntity> getDeudasUsuario(String value) {
        return usuarioDeudaRepo.getDeudasUsuario(value);
    }
}
