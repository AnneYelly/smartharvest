package com.upc.smartharvest.Security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TokenUtil {

    public static String generarToken(String username, String rol) {
        String data = username + ":" + rol + ":" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean tokenValido(String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return false;
            }

            String token = authorizationHeader.substring(7);
            String data = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
            String[] partes = data.split(":");

            return partes.length == 3;

        } catch (Exception e) {
            return false;
        }
    }
}