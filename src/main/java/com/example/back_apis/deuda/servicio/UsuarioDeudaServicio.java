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

    @Autowired
    private AuthServicio authServicio;

    @Autowired
    private ValidacionServicio validacion;

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
        System.out.println("llega aqui -------1");
        Respuesta respuesta = validacion.deudaEliminar(value);
        if (respuesta.getError() == 0) {
            return usuarioDeudaRepo.eliminarDeuda(value);
        } else {
            return Mono.just(ResponseEntity.status(200).body(respuesta.toString()));
        }

    }

    public Mono<ResponseEntity<String>> actualizarDeuda(String value) {
        Respuesta respuesta = validacion.deudaEditar(value);
        if (respuesta.getError() == 0) {
            return usuarioDeudaRepo.actualizarDeuda(value);
        } else {
            return Mono.just(ResponseEntity.status(200).body(respuesta.toString()));
        }

    }

    public Mono<ResponseEntity<String>> crearDeuda(String value) {
        Respuesta respuesta = validacion.deudaCrear(value);
        if (respuesta.getError() == 0) {
            return usuarioDeudaRepo.crearDeuda(value);
        } else {
            return Mono.just(ResponseEntity.status(200).body(respuesta.toString()));
        }
    }

    public Mono<ResponseEntity<String>> consultarDeudas(String value) {
        return usuarioDeudaRepo.consultarDeudas(value);
    }


    public Mono<ResponseEntity> autenticacion(String value) {
        return authServicio.getAuth(value);
    }

    public Mono<ResponseEntity<String>> autenticacionCierre(String value) {
        return usuarioDeudaRepo.authCierre(value);
    }
}
