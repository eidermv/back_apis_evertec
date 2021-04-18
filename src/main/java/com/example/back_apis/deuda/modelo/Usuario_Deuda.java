package com.example.back_apis.deuda.modelo;

import org.springframework.data.annotation.Id;

public class Usuario_Deuda {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
