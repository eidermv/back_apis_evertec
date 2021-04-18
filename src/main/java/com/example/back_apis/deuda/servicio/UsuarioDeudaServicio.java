package com.example.back_apis.deuda.servicio;

import com.example.back_apis.deuda.modelo.Respuesta;
import com.example.back_apis.deuda.repositorio.UsuarioDeudaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UsuarioDeudaServicio {

    @Autowired
    private UsuarioDeudaRepo usuarioDeudaRepo;

    public Mono<ResponseEntity<String>> getDeudasUsuario(String value) {
        return usuarioDeudaRepo.getDeudasUsuario(value);
    }

    public Mono<ResponseEntity<String>> getUsuarios(String value) {
        return usuarioDeudaRepo.getUsuarios(value);
    }

    public Mono<ResponseEntity<String>> getInsUpdUsuario(String value) {
        return usuarioDeudaRepo.getInsUpdUsuario(value);
    }

    public Mono<ResponseEntity<String>> eliminarDeuda(String value) {
        return usuarioDeudaRepo.eliminarDeuda(value);
    }

    public Mono<ResponseEntity<String>> autenticacion(String value) {
        return usuarioDeudaRepo.auth(value);
    }

    public Mono<ResponseEntity<String>> autenticacionCierre(String value) {
        return usuarioDeudaRepo.authCierre(value);
    }
}
