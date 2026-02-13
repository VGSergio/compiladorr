package com.vgs.compilador.manager;

import com.vgs.compilador.symbols.SymbolBase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author sergi
 */
public class ErrorManager {

    private static final List<String> errors = new ArrayList<>();
    private static final int LEXICAL_ERROR = 0;
    private static final int SYNTAX_ERROR = 1;
    private static final int SEMANTIC_ERROR = 2;
    private static final int[] errorCounters = new int[3];
    private static final String horizontalLine = "═".repeat(100);
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static boolean showInConsole = true;

    public static void setShowInConsole(boolean show) {
        showInConsole = show;
    }

    public static void lexical(int line, int column, String token) {
        errorCounters[LEXICAL_ERROR]++;
        // Versión del error en consola
        if (showInConsole) {
            String errorConsole = String.format("""
                    %s%s%s%s
                    %s %sERROR LÉXICO%s
                    %s%s%s%s
                    %s Posición:%s Línea %d, Columna %d
                    %s Token:%s '%s'
                    %s Información:%s No se reconoce el token
                    %s%s%s%s
                    """, RED, BOLD, horizontalLine, RESET, RED, BOLD, RESET, RED, BOLD, horizontalLine, RESET, YELLOW, RESET, line, column, YELLOW, RESET, token, YELLOW, RESET, RED, BOLD, horizontalLine, RESET);
            System.err.print(errorConsole);
        }

        // Versión del error en el fichero
        String msgError = String.format("[ERROR LÉXICO] Token %s no reconocido en %d:%d\n", token, line, column);
        errors.add(msgError);
    }

    public static void syntax(int line, int column, String symbol, LinkedList<String> expected_tokens, boolean isFatal) {
        errorCounters[SYNTAX_ERROR]++;
        // Versión del error en consola
        String msgError;
        if (showInConsole) {
            if (isFatal) {
                msgError = String.format("""
                        %s%s%s%s
                        %s %sERROR SINTÁCTICO%s
                        %s%s%s%s
                        %s Posición:%s Línea %d, Columna %d
                        %s Encontrado:%s '%s'
                        %s Información:%s No se pudo reparar y continuar el análisis
                        %s%s%s%s
                        """, RED, BOLD, horizontalLine, RESET, RED, BOLD, RESET, RED, BOLD, horizontalLine, RESET, YELLOW, RESET, line, column, YELLOW, RESET, symbol, YELLOW, RESET, RED, BOLD, horizontalLine, RESET);
            } else {
                String expectedFormatted = formatTokens(expected_tokens);
                msgError = String.format("""
                        %s%s%s%s
                        %s %sERROR SINTÁCTICO%s
                        %s%s%s%s
                        %s Posición:%s Línea %d, Columna %d
                        %s Encontrado:%s '%s'
                        %s Esperado:%s %s
                        %s%s%s%s
                        """, RED, BOLD, horizontalLine, RESET, RED, BOLD, RESET, RED, BOLD, horizontalLine, RESET, YELLOW, RESET, line, column, YELLOW, RESET, symbol, YELLOW, RESET, expectedFormatted, RED, BOLD, horizontalLine, RESET);
            }
            System.err.print(msgError);
        }
        if (isFatal) {
            msgError = String.format("""
                    [ERROR SINTÁCTICO] No se pudo reparar y continuar el análisis para el
                    símbolo de entrada "%s" en %d:%d
                    """, symbol, line, column);
            errors.add(msgError);
        } else {
            msgError = String.format("""
                    [ERROR SINTÁCTICO] Error sintáctico para símbolo de entrada "%s" en %d:%d
                    las clases de token esperados son %s
                    """, symbol, line, column, expected_tokens);
            errors.add(msgError);
        }
    }

    private static String formatTokens(LinkedList<String> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            return "nada";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            result.append("'").append(token).append("'");
            if (i < tokens.size() - 1) {
                if (i == tokens.size() - 2) {
                    result.append(" o ");
                } else {
                    result.append(", ");
                }
            }
        }

        return result.toString();
    }

    public static void semantic(String msg) {
        errorCounters[SEMANTIC_ERROR]++;
        String msgError;
        if (showInConsole) {
            msgError = String.format("""
                    %s%s%s%s
                    %s %sERROR SEMÁNTICO%s
                    %s%s%s%s
                    %s Información:%s %s
                    %s%s%s%s
                    """, RED, BOLD, horizontalLine, RESET, RED, BOLD, RESET, RED, BOLD, horizontalLine, RESET, YELLOW, RESET, msg, RED, BOLD, horizontalLine, RESET);
            System.err.println(msgError);
        }
        msgError = String.format("[ERROR SEMÁNTICO] %s", msg);
        errors.add(msgError);
    }

    public static void semantic(int line, int column, String msg) {
        errorCounters[SEMANTIC_ERROR]++;
        String msgError;
        if (showInConsole) {
            msgError = String.format("""
                    %s%s%s%s
                    %s %sERROR SEMÁNTICO%s
                    %s%s%s%s
                    %s Posición:%s Línea %d, Columna %d
                    %s Información:%s %s
                    %s%s%s%s
                    """, RED, BOLD, horizontalLine, RESET, RED, BOLD, RESET, RED, BOLD, horizontalLine, RESET, YELLOW, RESET, line, column, YELLOW, RESET, msg, RED, BOLD, horizontalLine, RESET);
            System.err.println(msgError);
        }
        msgError = String.format("[ERROR SEMÁNTICO] %s en %d:%d", msg, line, column);
        errors.add(msgError);
    }

    public static void semantic(SymbolBase symbol, String msg) {
        if (symbol != null) {
            semantic(symbol.getLine(), symbol.getColumn(), msg);
        } else {
            semantic(msg);
        }
    }

    public static void dump() {
        try {
            // Crear directorio si no existe
            Path filePath = Paths.get("out/errores.txt");
            Path parentDir = filePath.getParent();

            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            List<String> allLines = new ArrayList<>();

            if (!hasErrors()) {
                allLines.add("Sin errores");
                Files.write(filePath, allLines);
                return;
            }

            allLines.add("=".repeat(120));
            allLines.add("ERRORES DE COMPILACIÓN");
            allLines.add("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            allLines.add("=".repeat(120));
            allLines.addAll(errors);
            allLines.add("=".repeat(120));
            allLines.add("Total de errores: " + errors.size());

            // Escribir en archivo
            Files.write(filePath, allLines);
        } catch (IOException e) {
            System.err.println("Error escribiendo o creando archivo de errores: " + e.getMessage());
        }
    }

    public static boolean hasErrors() {
        return !errors.isEmpty();
    }

    public static boolean hasLexicalErrors() {
        return errorCounters[LEXICAL_ERROR] > 0;
    }

    public static boolean hasSyntaxErrors() {
        return errorCounters[SYNTAX_ERROR] > 0;
    }

    public static boolean hasSemanticErrors() {
        return errorCounters[SEMANTIC_ERROR] > 0;
    }

    public static int getLexicalErrorCount() {
        return errorCounters[LEXICAL_ERROR];
    }

    public static int getSyntaxErrorCount() {
        return errorCounters[SYNTAX_ERROR];
    }

    public static int getSemanticErrorCount() {
        return errorCounters[SEMANTIC_ERROR];
    }
}
