package com.example.back_apis.deuda.controlador;

import com.example.back_apis.deuda.modelo.Respuesta;
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

    @GetMapping(value = "/listarDeudas", produces = "application/json")
    public Mono<ResponseEntity<String>> listar(@RequestParam String value) {
        return usuarioDeudaServicio.getDeudasUsuario(value);
    }
}
