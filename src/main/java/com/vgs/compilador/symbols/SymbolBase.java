package com.vgs.compilador.symbols;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public abstract class SymbolBase extends ComplexSymbol {

    private static int idAutoIncrement = 0;

    public SymbolBase(String name) {
        super(name, idAutoIncrement++);
    }

    public SymbolBase(String name, Location left, Location right) {
        super(name, idAutoIncrement++, left, right);
    }

    public SymbolBase(String name, Location left, Location right, Object value) {
        super(name, idAutoIncrement++, left, right, value);
    }

    public int getLine() {
        return getLeft().getLine();
    }

    public int getColumn() {
        return getRight().getColumn();
    }

    @Override
    public abstract String toString();
}
