package com.upc.smartharvest.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String passwordPlano) {
        return encoder.encode(passwordPlano);
    }

    public static boolean verificarPassword(String passwordPlano, String passwordHash) {
        return encoder.matches(passwordPlano, passwordHash);
    }

    public static boolean esHashBCrypt(String password) {
        return password != null &&
                (password.startsWith("$2a$") ||
                        password.startsWith("$2b$") ||
                        password.startsWith("$2y$"));
    }
}