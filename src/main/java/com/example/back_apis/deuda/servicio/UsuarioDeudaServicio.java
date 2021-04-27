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

    /*
    ================================================
    Metodo que me permite realizar consulta de deudas por identificacion de usuario
    ================================================
     */
    public Mono<ResponseEntity<String>> getDeudasUsuario(String value) {
        return usuarioDeudaRepo.getDeudasUsuario(value);
    }

    /*
    ================================================
    Metodo que me permite listar los usuarios
    ================================================
     */
    public Mono<ResponseEntity<String>> getUsuarios(String value) {
        return usuarioDeudaRepo.getUsuarios(value);
    }

    /*
    ================================================
    Metodo que me permite realizar insercion o actualizacion de datos de usuario o deuda
    ================================================
     */
    public Mono<ResponseEntity<String>> getInsUpdUsuario(String value) {
        return usuarioDeudaRepo.getInsUpdUsuario(value);
    }

    /*
    ================================================
    Metodo que me permite eliminar una deuda
    ================================================
     */
    public Mono<ResponseEntity<String>> eliminarDeuda(String value) {
        System.out.println("llega aqui -------1");
        Respuesta respuesta = validacion.deudaEliminar(value);
        if (respuesta.getError() == 0) {
            return usuarioDeudaRepo.eliminarDeuda(value);
        } else {
            return Mono.just(ResponseEntity.status(200).body(respuesta.toString()));
        }

    }

    /*
    ================================================
    Metodo que me permite actualizar una deuda
    ================================================
     */
    public Mono<ResponseEntity<String>> actualizarDeuda(String value) {
        Respuesta respuesta = validacion.deudaEditar(value);
        if (respuesta.getError() == 0) {
            return usuarioDeudaRepo.actualizarDeuda(value);
        } else {
            return Mono.just(ResponseEntity.status(200).body(respuesta.toString()));
        }

    }

    /*
    ================================================
    Metodo que me permite crear una deuda
    ================================================
     */
    public Mono<ResponseEntity<String>> crearDeuda(String value) {
        Respuesta respuesta = validacion.deudaCrear(value);
        if (respuesta.getError() == 0) {
            return usuarioDeudaRepo.crearDeuda(value);
        } else {
            return Mono.just(ResponseEntity.status(200).body(respuesta.toString()));
        }
    }

    /*
    ================================================
    Metodo que me permite realizar consultas de deudas
    ================================================
     */
    public Mono<ResponseEntity<String>> consultarDeudas(String value) {
        return usuarioDeudaRepo.consultarDeudas(value);
    }


    /*
    ================================================
    Metodo que me permite realizar la autenticacion
    ================================================
     */
    public Mono<ResponseEntity> autenticacion(String value) {
        return authServicio.getAuth(value);
    }

    /*
    ================================================
    Metodo que me permite realizar el cierre de sesion
    ================================================
     */
    public Mono<ResponseEntity<String>> autenticacionCierre(String value) {
        return usuarioDeudaRepo.authCierre(value);
    }
}
