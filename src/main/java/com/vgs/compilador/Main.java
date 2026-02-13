package com.vgs.compilador;

import com.vgs.compilador.manager.ErrorManager;
import com.vgs.compilador.manager.TokenManager;
import com.vgs.compilador.symbols.SymbolMain;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.SymbolFactory;

/**
 *
 * @author sergi
 */
public class Main {

    static void main(String[] args) {
        // Cleans previous output
//        try {
//            File outDir = new File("out");
//            if (outDir.exists()) {
//                FileUtils.cleanDirectory(outDir);
//            }
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }

        // Sets whether error should be shown on console or not
        ErrorManager.setShowInConsole(true);

        if (args.length == 1) {
            if (compile(args[0])) {
                System.out.println("Compilación exitosa");
            } else {
                System.err.println("Compilación fallida");
            }
        } else {
            System.out.println("Uso: java -jar compiler.jar <archivo.txt>");
            System.out.println("Ejemplo: java -jar compiler.jar programa.txt");
        }

        // Generate out files
        TokenManager.dump();
        ErrorManager.dump();
    }

    private static boolean compile(String file_path) {
        SymbolMain symbolMain = lexicalAndSyntaxAnalysis(file_path);
        if (symbolMain == null) {
            System.err.println("No es posible analizar semánticamente el archivo | SymbolMain == null");
            return false;
        }

//        TypeChecker semanticAnalyzer = new TypeChecker();
//        semanticAnalyzer.manage(symbolMain);
        if (ErrorManager.hasSemanticErrors()) {
            System.err.println("No es posible generar el c3@ | El código tiene errores semánticos");
            return false;
        }

//        c3a intermediateCode = new c3a();
//        intermediateCode.manage(symbolMain);
        return true;
    }

    private static SymbolMain lexicalAndSyntaxAnalysis(String file_path) {
        Object symbolMain = null;
        try {
            Reader input = new FileReader(file_path);

            // Análisis léxico / sintáctico
            SymbolFactory sf = new ComplexSymbolFactory();
            Scanner scanner = new Scanner(input);
            Parser parser = new Parser(scanner, sf);
            symbolMain = parser.parse().value;

            // Si el archivo contiene errores no hacemos el análisis semántico
            if (ErrorManager.hasErrors()) {
                System.err.println("Error compilando");
                if (ErrorManager.hasLexicalErrors()) {
                    System.err.printf("Fallos léxicos: %d.\n", ErrorManager.getLexicalErrorCount());
                } else if (ErrorManager.hasSyntaxErrors()) {
                    System.err.printf("Fallos sintácticos: %d.\n", ErrorManager.getSyntaxErrorCount());
                }
                return null;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + file_path);
        } catch (Exception e) {
            System.err.println("Error analizando el archivo: " + file_path);
        }
        return (SymbolMain) symbolMain;
    }
}
