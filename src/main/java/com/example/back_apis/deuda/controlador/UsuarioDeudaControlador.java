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

    /*
    ================================================
    Metodo que me permite listar deudas por identificacion
    {"identificacion": "1234"}
    ================================================
     */
    @GetMapping(value = "/listarDeudas", produces = "application/json")
    public Mono listarDeudaUsuario(@RequestParam String value, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.getDeudasUsuario(value);
        } else {
            return Mono.just(responseEntity);
        }

    }

    /*
    ================================================
    Metodo que me permite obtener todos los usuarios
    ''
    ================================================
     */
    @GetMapping(value = "/listarUsuario", produces = "application/json")
    public Mono listarUsuarios(@RequestParam String value, @RequestHeader("Authorization") String auth) {

        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.getUsuarios(value);
        } else {
            return Mono.just(responseEntity);
        }
    }

    /*
    ================================================
    Metodo que me permite crear o actualizar una deuda
    {"identificacion":"1234", "id_deuda": "1238", "monto": 21001, "fecha_vencimiento":"2021-04-30 14:47:28"}
    ================================================
     */
    @PostMapping(value = "/agregarActUsDe", produces = "application/json")
    public Mono agregarActUsDe(@RequestBody String body, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.getInsUpdUsuario(body);
        } else {
            return Mono.just(responseEntity);
        }
    }

    /*
    ================================================
    Metodo que me permite actualizar deuda
    {"id_deuda": "1238", "monto": 30001, "fecha_vencimiento":"2021-04-30 14:47:28"}
    ================================================
     */
    @PutMapping(value = "/actualizarDeuda", produces = "application/json")
    public Mono actualizarDeuda(@RequestBody String body, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.actualizarDeuda(body);
        } else {
            return Mono.just(responseEntity);
        }
    }

    /*
    ================================================
    Metodo que me permite crear una deuda a un usuario
    {"identificacion":"1234", "id_deuda": "1238", "monto": 21001, "fecha_vencimiento":"2021-04-30 14:47:28"}
    ================================================
     */
    @PostMapping(value = "/crearDeuda", produces = "application/json")
    public Mono crearDeuda(@RequestBody String body, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.crearDeuda(body);
        } else {
            return Mono.just(responseEntity);
        }
    }

    /*
    ================================================
    Metodo que me permite eliminar una deuda
    {"id_deuda": "123b"}
    ================================================
     */
    @DeleteMapping(value = "/eliminarDeuda", produces = "application/json")
    public Mono eliminarDeuda(@RequestParam String value, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.eliminarDeuda(value);
        } else {
            return Mono.just(responseEntity);
        }
    }

    /*
    ================================================
    Metodo que me permite iniciar sesion
    Authorization Basic [codigo]
    ================================================
     */
    @PostMapping(value = "/auth", produces = "application/json")
    public Mono<ResponseEntity> autenticacion(@RequestHeader("Authorization") String auth) {
        return usuarioDeudaServicio.autenticacion(auth);
    }

    /*
    ================================================
    Metodo que me permite cerrar sesion
    {"id_login": "1"}
    ================================================
     */
    @PutMapping(value = "/refreshAuth", produces = "application/json")
    public Mono refrescarToken(@RequestHeader("Authorization") String auth) {
        return usuarioDeudaServicio.autenticacion(auth);
    }

    /*
    ================================================
    Metodo que me permite cerrar sesion
    {"id_login": "1"}
    ================================================
     */
    @GetMapping(value = "/authCierre", produces = "application/json")
    public Mono autenticacionCierre(@RequestParam String value, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.autenticacionCierre(value);
        } else {
            return Mono.just(responseEntity);
        }
    }

    /*
    ================================================
    Metodo que me permite consultar
    {"identificacion":"","monto":"","fecha_carga":"2021-04-26"}
    ================================================
     */
    @GetMapping(value = "/consultarDeudas", produces = "application/json")
    public Mono consultarDeudas(@RequestParam String value, @RequestHeader("Authorization") String auth) {
        ResponseEntity responseEntity = authServicio.comprobarAuth(auth);
        if (responseEntity.getStatusCodeValue() == 200 ) {
            return usuarioDeudaServicio.consultarDeudas(value);
        } else {
            return Mono.just(responseEntity);
        }
    }
}
