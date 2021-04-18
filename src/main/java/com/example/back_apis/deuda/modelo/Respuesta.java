package com.example.back_apis.deuda.modelo;

public class Respuesta {
    private int error;
    private String mensaje;
    private String data;


    public Respuesta(int error, String mensaje, String data) {
        this.error = error;
        this.mensaje = mensaje;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
