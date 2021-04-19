package com.example.back_apis.deuda.controlador;

import com.example.back_apis.deuda.servicio.AuthServicio;
import com.example.back_apis.deuda.servicio.UsuarioDeudaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/usuario")
public class UsuarioDeudaControlador {
    @Autowired
    private UsuarioDeudaServicio usuarioDeudaServicio;

    @Autowired
    private AuthServicio authServicio;

    @GetMapping(value = "/listarDeudas", produces = "application/json")
    public Mono listarDeudaUsuario(@RequestParam String value, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.getDeudasUsuario(value);
        } else {
            return Mono.just(responseEntity);
        }

    }

    @GetMapping(value = "/listarUsuario", produces = "application/json")
    public Mono listarUsuarios(@RequestParam String value, @RequestHeader("Authorization") String auth) {

        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.getUsuarios(value);
        } else {
            return Mono.just(responseEntity);
        }
    }

    @PostMapping(value = "/agregarActUsDe", produces = "application/json")
    public Mono agregarActUsDe(@RequestBody String body, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.getInsUpdUsuario(body);
        } else {
            return Mono.just(responseEntity);
        }
    }

    @DeleteMapping(value = "/eliminarDeuda", produces = "application/json")
    public Mono eliminarDeuda(@RequestParam String value, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.eliminarDeuda(value);
        } else {
            return Mono.just(responseEntity);
        }
    }

    @PostMapping(value = "/auth", produces = "application/json")
    public Mono<ResponseEntity> autenticacion(@RequestHeader("Authorization") String auth) {
        return usuarioDeudaServicio.autenticacion(auth);
    }

    @GetMapping(value = "/authCierre", produces = "application/json")
    public Mono autenticacionCierre(@RequestParam String value, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.autenticacionCierre(value);
        } else {
            return Mono.just(responseEntity);
        }
    }
}
