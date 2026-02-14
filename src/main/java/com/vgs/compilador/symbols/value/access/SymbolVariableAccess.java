package com.vgs.compilador.symbols.value.access;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Representa el acceso a una variable simple en el AST.
 * Ejemplo: x, contador, nombre
 * @author sergi
 */
public class SymbolVariableAccess extends SymbolAccess {

    public SymbolVariableAccess(String identifier, Location left, Location right) {
        super("VariableAccess", identifier, left, right);
    }

    @Override
    public String toString() {
        return identifier;
    }
}
