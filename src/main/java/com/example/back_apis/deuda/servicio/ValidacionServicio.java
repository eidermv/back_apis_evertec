package com.example.back_apis.deuda.servicio;

import com.example.back_apis.deuda.modelo.Respuesta;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ValidacionServicio {
    /*
    ================================================
    Metodo que me permite realizar la validacion de campos entrantes para crear
    ================================================
     */
    public Respuesta deudaCrear(String deuda) {
        JsonObject d = new Gson().fromJson(deuda, JsonObject.class);
        int contador = 0;
        try {
            if (d.get("monto") != null) {
                if (d.get("monto").getAsInt() >= 0){
                    if (d.get("monto").getAsString().length()<20) {
                        contador++;
                    } else {
                        return new Respuesta(-2, "Monto supera tamaño maximo", null);
                    }

                } else {
                    return new Respuesta(-3, "Monto tiene valor negativo", null);
                }
                /*try {
                    Integer.parseInt(identificacion);
                } catch (NumberFormatException nfe){
                    return "Identificacion debe ser numerica";
                }*/

            } else {
                return new Respuesta(-4, "Monto es requerido", null);
            }

            if (d.get("id_deuda") != null) {
                if (d.get("id_deuda").getAsString().length()<15) {
                    contador++;
                } else {
                    return new Respuesta(-2, "Id_deuda supera tamaño maximo", null);
                }
            } else {
                return new Respuesta(-4, "Id_deuda es requerido", null);
            }

            if (d.get("fecha_vencimiento") != null) {
                if (d.get("fecha_vencimiento").getAsString().length()>=10 ) {
                        //&& d.get("fecha_vencimiento").getAsString().matches("/^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$/")) {
                    contador++;
                } else {
                    return new Respuesta(-2, "Fecha vencimiento formato invalido", null);
                }
            } else {
                return new Respuesta(-4, "Fecha vencimiento es requerido", null);
            }

            if (contador == 3) {
                return new Respuesta(0, "Correcto", null);
            } else {
                return new Respuesta(-5, "No se superaron todas las validaciones", null);
            }
        } catch (UnsupportedOperationException e) {
            return new Respuesta(-1, "Faltan campos correspondiente a deuda", null);
        }
    }

    /*
    ================================================
    Metodo que me permite realizar la validacion de campos entrantes para editar
    ================================================
     */
    public Respuesta deudaEditar(String deuda) {
        JsonObject d = new Gson().fromJson(deuda, JsonObject.class);
        int contador = 0;
        try {
            if (d.get("monto") != null) {
                if (d.get("monto").getAsInt() >= 0){
                    if (d.get("monto").getAsString().length()<20) {
                        contador++;
                    } else {
                        return new Respuesta(-2, "Monto supera tamaño maximo", null);
                    }

                } else {
                    return new Respuesta(-3, "Monto tiene valor negativo", null);
                }
                /*try {
                    Integer.parseInt(identificacion);
                } catch (NumberFormatException nfe){
                    return "Identificacion debe ser numerica";
                }*/

            }

            if (d.get("id_deuda") != null) {
                if (d.get("id_deuda").getAsString().length()<15) {
                    contador++;
                } else {
                    return new Respuesta(-2, "Id_deuda supera tamaño maximo", null);
                }
            } else {
                return new Respuesta(-4, "Id_deuda es requerido", null);
            }

            if (d.get("fecha_vencimiento") != null) {
                if (d.get("fecha_vencimiento").getAsString().length()>=10 ) {
                        //&& d.get("fecha_vencimiento").getAsString().matches("^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$")) {
                    contador++;
                } else {
                    return new Respuesta(-2, "Fecha vencimiento formato invalido", null);
                }
            }

            if (contador == 3) {
                return new Respuesta(0, "Correcto", null);
            } else {
                return new Respuesta(-5, "No se superaron todas las validaciones", null);
            }
        } catch (UnsupportedOperationException e) {
            return new Respuesta(-1, "Faltan campos correspondiente a deuda", null);
        }
    }

    /*
    ================================================
    Metodo que me permite realizar la validacion de campos entrantes para eliminar
    ================================================
     */
    public Respuesta deudaEliminar(String deuda) {

        JsonObject d = new Gson().fromJson(deuda.replace("'", ""), JsonObject.class);

        int contador = 0;
        try {

            if (d.get("id_deuda") != null) {
                if (d.get("id_deuda").getAsString().length()<15) {
                    contador++;
                } else {
                    return new Respuesta(-2, "Id_deuda supera tamaño maximo", null);
                }
            } else {
                return new Respuesta(-4, "Id_deuda es requerido", null);
            }

            if (contador == 1) {
                return new Respuesta(0, "Correcto", null);
            } else {
                return new Respuesta(-5, "No se superaron todas las validaciones", null);
            }
        } catch (UnsupportedOperationException e) {
            return new Respuesta(-1, "Faltan campos correspondiente a deuda", null);
        }
    }
}
