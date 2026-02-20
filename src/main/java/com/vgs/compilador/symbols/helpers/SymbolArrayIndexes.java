package com.vgs.compilador.symbols.helpers;

import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.type.SymbolType;
import com.vgs.compilador.symbols.value.SymbolValue;
import java.util.ArrayList;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolArrayIndexes extends SymbolBase {

    private final SymbolType type;
    private final ArrayList<SymbolValue> lengths;

    public SymbolArrayIndexes(SymbolType type, ArrayList<SymbolValue> lengths, Location left, Location right) {
        super("ArrayLenght", left, right);
        this.type = type;
        this.lengths = lengths;
    }

    public SymbolType getType() {
        return type;
    }

    public SymbolValue[] getLengths() {
        return lengths.toArray(SymbolValue[]::new);
    }

    public int getDimensions() {
        return lengths.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("new %s", type));
        for (SymbolValue length : lengths) {
            sb.append('[').append(length).append(']');
        }
        return sb.toString();
    }
}
