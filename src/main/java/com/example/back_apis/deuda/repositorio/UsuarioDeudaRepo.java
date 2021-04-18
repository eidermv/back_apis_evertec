package com.example.back_apis.deuda.repositorio;

import com.example.back_apis.deuda.modelo.Conexion;
import com.example.back_apis.deuda.modelo.Respuesta;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UsuarioDeudaRepo {

    private final Conexion conexion = new Conexion();

    public Flux<ResponseEntity> getDeudasUsuario(String value) {
        return conexion.ejecutarProcedimiento("SP_DEUDAS_USUARIO", value)
                .flatMap(jsonObject ->
                        Mono.just(ResponseEntity.status(200).body(new Respuesta(0, "Deudas obtenidas", jsonObject.get("data").getAsString())))
                );
    }
}
