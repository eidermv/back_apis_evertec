package com.example.back_apis.deuda.servicio;

import com.example.back_apis.deuda.modelo.Respuesta;
import com.example.back_apis.deuda.repositorio.UsuarioDeudaRepo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthServicio {

    Base64.Decoder b64dec = Base64.getDecoder();
    @Value("${property.SECRET_KEY}")
    private String SECRET_KEY;

    private static final String JWT_ISSUER = "Evertec Inc";
    private static final String JWT_ID_HEADER = "EVT-";

    @Autowired
    private UsuarioDeudaRepo usuarioDeudaRepo;


    /*
    ================================================
    Metodo que verifica informacion y dependiente del tipo de autenticacion crea un token
    ================================================
     */
    public Mono<ResponseEntity> getAuth(String auth) {

        try {
            String[] authStr = auth.split(" ");

            String jwtIdCode = "evertec:" + Long.toString(System.currentTimeMillis());

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(jwtIdCode.getBytes("utf8"));

            jwtIdCode = JWT_ID_HEADER + String.format("%040X", new BigInteger(1, digest.digest()));

            switch (authStr[0]) {
                case "Basic" -> {
                    if (authStr.length > 1) {
                        authStr[1] = new String(b64dec.decode(authStr[1].getBytes("UTF-8")), "UTF-8");
                        // System.out.println("------ " + authStr[1]);
                        //JsonObject jsonObject = new JsonObject();
                        //jsonObject.addProperty("usuario", authStr[1].split(":")[0].trim());
                        //jsonObject.addProperty("contrasena", authStr[1].split(":")[1].trim());
                        Mono<ResponseEntity<String>> user1 = usuarioDeudaRepo.auth("'"+authStr[1]+"'");


                        String finalJwtIdCode = jwtIdCode;
                        return user1.doOnNext(System.out::println).map(serverResponse -> {
                            if (serverResponse.getStatusCodeValue() == 200) {

                                JsonObject usrJson = new Gson().fromJson(serverResponse.getBody(), JsonObject.class);
                                try {
                                    if (usrJson.get("error").getAsInt() == 0) {
                                        //String username1 = usrJson.get("username").getAsString();
                                        String role = "admin";
                                        int tiempo = 1800000;//54000000; //15 horas
                                        String payload = usrJson.get("data").toString();

                                        JsonObject responseJson1 = new JsonObject();
                                        JsonObject dataJson1 = new JsonObject();

                                        //if (user.getStatus()) {
                                        String jwtStr = createJWT("Evertec-prueba", finalJwtIdCode, JWT_ISSUER, payload,
                                                role, tiempo);
                                        responseJson1.addProperty("mensaje", "Token privado generado");
                                        responseJson1.addProperty("error", 0);
                                        //responseJson1.addProperty("data", usrJson.get("data").toString());
                                        responseJson1.addProperty("key", jwtStr);
                                        dataJson1 = new Gson().fromJson(payload, JsonObject.class);
                                        responseJson1.add("data", dataJson1);
                                        return ResponseEntity.status(200).body(responseJson1.toString());
                                    } else {
                                        return ResponseEntity.status(200).body(new Respuesta(-1, "Error ejecutando SP, " + usrJson.get("mensaje").getAsString(), null));
                                    }

                                /*} else {
                                    throw new Exception("Usuario no válido");
                                }*/
                                } catch (UnsupportedOperationException e) {
                                    return ResponseEntity.status(200).body(new Respuesta(-1, "Servicio no disponible", null));
                                }


                            } else {
                                try {
                                    throw new Exception("Sin respuesta del servidor de validación");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return ResponseEntity.status(401).body(new Respuesta(-1, "Sin respuesta del servidor de validación", null));

                                //return serverResponse;
                            }
                            //return Mono.just(serverResponse);

                        });


                    } else {
                        try {
                            throw new Exception("Sin datos de autenticación");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Mono.just(ResponseEntity.status(401).body(new Respuesta(-1, "Sin datos de autenticación", null)));
                    }
                }
                case "Refresh" -> {
                    if (authStr.length > 1) {
                        authStr[1] = new String(b64dec.decode(authStr[1].getBytes("UTF-8")), "UTF-8");

                        Mono<ResponseEntity<String>> user1 = usuarioDeudaRepo.refreshAuth("'"+authStr[1]+"'");


                        String finalJwtIdCode = jwtIdCode;
                        return user1.doOnNext(System.out::println).map(serverResponse -> {
                            if (serverResponse.getStatusCodeValue() == 200) {

                                JsonObject usrJson = new Gson().fromJson(serverResponse.getBody(), JsonObject.class);
                                try {
                                    if (usrJson.get("error").getAsInt() == 0) {
                                        //String username1 = usrJson.get("username").getAsString();
                                        String role = "admin";
                                        int tiempo = 1800000;//54000000; //15 horas
                                        String payload = usrJson.get("data").toString();

                                        JsonObject responseJson1 = new JsonObject();
                                        JsonObject dataJson1 = new JsonObject();

                                        //if (user.getStatus()) {
                                        String jwtStr = createJWT("Evertec-prueba", finalJwtIdCode, JWT_ISSUER, payload,
                                                role, tiempo);
                                        responseJson1.addProperty("mensaje", "Token actualizado");
                                        responseJson1.addProperty("error", 0);
                                        //responseJson1.addProperty("data", usrJson.get("data").toString());
                                        responseJson1.addProperty("key", jwtStr);
                                        dataJson1 = new Gson().fromJson(payload, JsonObject.class);
                                        responseJson1.add("data", dataJson1);
                                        return ResponseEntity.status(200).body(responseJson1.toString());
                                    } else if (usrJson.get("error").getAsInt() == -1) {
                                        return ResponseEntity.status(401).body(new Respuesta(-1, "Error ejecutando SP, " + usrJson.get("mensaje").getAsString(), null));
                                    } else {
                                        return ResponseEntity.status(401).body(new Respuesta(-1, "" + usrJson.get("mensaje").getAsString(), null));
                                    }

                                /*} else {
                                    throw new Exception("Usuario no válido");
                                }*/
                                } catch (UnsupportedOperationException e) {
                                    return ResponseEntity.status(200).body(new Respuesta(-1, "Servicio no disponible", null));
                                }


                            } else {
                                try {
                                    throw new Exception("Sin respuesta del servidor de validación");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return ResponseEntity.status(401).body(new Respuesta(-1, "Sin respuesta del servidor de validación", null));

                                //return serverResponse;
                            }
                            //return Mono.just(serverResponse);

                        });


                    } else {
                        try {
                            throw new Exception("Sin datos de autenticación");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Mono.just(ResponseEntity.status(401).body(new Respuesta(-1, "Sin datos de autenticación", null)));
                    }
                }
                default -> {
                    //responseJson.addProperty("mensaje", "Usuario no autorizado");
                    //responseJson.addProperty("error", -2);
                    //return Response.status(401).entity(responseJson.toString()).build();
                    return Mono.just(ResponseEntity.status(401).body(new Respuesta(-2, "Usuario no se puede autorizar", null)));

                }
            }

        } catch (Exception e) {
            System.out.println("Exception en auth: " + e.toString());
            //responseJson.addProperty("mensaje", "Error al autenticar");
            //responseJson.addProperty("error", -1);
            //return Response.status(401).entity(responseJson.toString()).build();
            return Mono.just(ResponseEntity.status(401).body(new Respuesta(-1, "Error al autenticar", null)));

        }

    }



    public String createJWT(String origin, String id, String issuer, String subject, String audience, long ttlMillis) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);


            //System.out.println(" KEY " + SECRET_KEY);
            byte[] apiKeySecretBytes = SECRET_KEY.getBytes();
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


            JwtBuilder builder = Jwts.builder().setHeaderParam("origin", origin).setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer).setAudience(audience).signWith(signatureAlgorithm, signingKey);


            if (ttlMillis >= 0) {
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }

            return builder.compact();
        } catch (Exception e) {
            System.out.println("Error al validar servicio, " +  e);
            return null;
        }
    }

    private Claims decodeJWT(String jwt) {
        System.out.println(jwt);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes()).build()
                .parseClaimsJws(jwt).getBody();
        //System.out.println("-- "+claims.getIssuer());
        //Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).build().parseClaimsJws(jwt).getBody();
        return claims;
    }

    public ResponseEntity comprobarAuth(String auth) {
        Claims jwtClaims = null;
        try {
            String[] authStr = auth.split(" ");
            if ("Bearer".equals(authStr[0])) {// Validación de Token
                try {
                    jwtClaims = decodeJWT(authStr[1]);
                    if (jwtClaims.getIssuer().equals(JWT_ISSUER)) {
                        return ResponseEntity.status(200).body(new Respuesta(0, "Usuario autorizado", null));
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    //System.out.println("El token no es válido");
                    return ResponseEntity.status(401).body(new Respuesta(-3, "Usuario no autorizado", null));
                }
            }
            return ResponseEntity.status(401).body(new Respuesta(-2, "Contexto de seguridad no autorizado", null));

        }
        catch(Exception e){
            return ResponseEntity.status(401).body(new Respuesta(-1, "Servicio no autorizado, inexistente o petición inválida", null));

        }
    }

}
