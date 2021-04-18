package com.example.back_apis;

import com.example.back_apis.deuda.repositorio.UsuarioDeudaRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackApisApplication {

    private static final UsuarioDeudaRepo usuarioDeudaRepo = new UsuarioDeudaRepo();

    public static void main(String[] args) {
        usuarioDeudaRepo.initConnectionFactory();
        usuarioDeudaRepo.initializeSchema();
        SpringApplication.run(BackApisApplication.class, args);
    }

}
