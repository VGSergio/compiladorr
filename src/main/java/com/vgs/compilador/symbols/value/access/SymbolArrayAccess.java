package com.vgs.compilador.symbols.value.access;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Representa el acceso a un elemento de array en el AST. Ejemplo: arr[0],
 * matriz[i][j]
 *
 * @author sergi
 */
public class SymbolArrayAccess extends SymbolVariableAccess {

    public SymbolArrayAccess(String identifier, Location left, Location right) {
        super(identifier, left, right);
    }

}
