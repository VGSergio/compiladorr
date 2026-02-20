package com.vgs.compilador.symbols.value.access;

import com.vgs.compilador.symbols.value.SymbolValue;
import java.util.ArrayList;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * Representa el acceso a un elemento de array en el AST. Ejemplo: arr[0],
 * matriz[i][j]
 *
 * @author sergi
 */
public class SymbolArrayAccess extends SymbolAccess {

    private final ArrayList<SymbolValue> indexes;

    public SymbolArrayAccess(String identifier, ArrayList<SymbolValue> indexes, Location left, Location right) {
        super("ArrayAccess", identifier, left, right);
        this.indexes = indexes;
    }

    public int getDimensions() {
        return indexes.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(identifier);
        for (SymbolValue index : indexes) {
            sb.append("[").append(index).append("]");
        }
        return sb.toString();
    }

}
