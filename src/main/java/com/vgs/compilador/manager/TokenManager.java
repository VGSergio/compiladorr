package com.vgs.compilador.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TokenManager {

    private static final List<String> tokens = new ArrayList<>();

    public static void add(String token) {
        tokens.add(token);
    }

    public static void dump() {
        try {
            // Crear directorio si no existe
            Path filePath = Paths.get("out/tokens.txt");
            Path parentDir = filePath.getParent();

            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            // Escribir en archivo
            Files.write(filePath, tokens);
        } catch (IOException e) {
            System.err.println("Error escribiendo o creando archivo de errores: " + e.getMessage());
        }
    }

}
