package com.example.back_apis.deuda.modelo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.r2dbc.spi.Batch;
import io.r2dbc.spi.Connection;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Conexion {
    // Connection Configuration
    private static MariadbConnectionConfiguration conf;
    private static MariadbConnectionFactory connFactory;
    private static Connection conn;

    /*
    ================================================
    Metodo que me permite realizar la conexion a la base de datos
    ================================================
     */
    public void initConnectionFactory() {

        try {
            // Configure the Connection
            conf = MariadbConnectionConfiguration.builder()
                    .host("localhost")
                    .port(3306)
                    .database("prueba_evertec")
                    .username("root")
                    .password("eider205")
                    .build();

            // Instantiate a Connection Factory
            connFactory = new MariadbConnectionFactory(conf);

        }
        catch (java.lang.IllegalArgumentException e) {
            System.err.println("Issue encountered while getting connection");
            e.printStackTrace();
        }
    }

    /*
    ================================================
    Metodo que me permite llamar un SP por medio de un batch
    ================================================
     */
    public Flux<String> ejecutarProcedimiento(String sp, String data) {

        try {
            initConnectionFactory();
            //Initialize a Connection
            conn = connFactory.create().block();

            String sql = "";

            //Create a Batch Object
            if (!data.equals("")) {
                data = "'" + data + "',";
                data = data.replace("''", "'");
            }
            System.out.println("-------------" + data);
            Batch batch = conn.createBatch();
            batch = batch.add("begin not atomic\n" +
                    "declare rta LONGTEXT default '';\n" +
                    "call "+sp+"("+data+" rta);\n" +
                    "select rta;\n" +
                    "end");
            /*Mono.from(batch.execute()).flatMap(result -> result.map( (row, metadata) -> {
                return String.format("- %s",
                        row.get(0, String.class));
            })).subscribe();*/
            return Flux.from(batch.execute()).flatMap(result -> result.map( (row, metadata) -> {
                //audArr.addProperty("error", 0);
                //audArr.addProperty("mensaje", "consulta exitosa");
                return row.get(0, String.class);
            }));
            /*for (String contact_entry : Flux.from(batch.execute()).flatMap(res -> res.map( (row, metadata) -> {
                JsonObject audArr = (new Gson()).fromJson(row.get(0, String.class), JsonObject.class);
                audArr.addProperty("error", 0);
                audArr.addProperty("mensaje", "consulta exitosa");
                return audArr.toString(); // Get First Name
            })).toIterable()) {
                System.out.println(contact_entry);
            };*/
            //Mono.from(batch.execute()).subscribe();
        }
        // Catch Exception
        catch (IllegalArgumentException e) {
            System.err.println("Issue running operation");
            e.printStackTrace();
        }finally { // Close Connection
            conn.close();
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("error", -1);
        jsonObject.addProperty("mensaje", "Error consultando");
        jsonObject.add("data", new JsonArray());
        return Flux.just(jsonObject.toString());
    }
}
