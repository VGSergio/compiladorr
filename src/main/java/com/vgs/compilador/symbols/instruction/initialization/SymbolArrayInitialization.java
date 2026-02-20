package com.vgs.compilador.symbols.instruction.initialization;

import com.vgs.compilador.symbols.helpers.SymbolArrayIndexes;
import com.vgs.compilador.symbols.helpers.SymbolArrayValues;
import com.vgs.compilador.symbols.instruction.SymbolInstruction;
import com.vgs.compilador.symbols.type.SymbolType;
import com.vgs.compilador.symbols.type.SymbolTypeArray;
import com.vgs.compilador.symbols.value.SymbolValue;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolArrayInitialization extends SymbolInstruction {

    private final SymbolTypeArray type;
    private final String identifier;
    private final SymbolArrayIndexes lengths;
    private final SymbolArrayValues values;

    // Array sin valores
    public SymbolArrayInitialization(SymbolTypeArray type, String identifier, SymbolArrayIndexes lengths, Location left, Location right) {
        super("ArrayInitialization", left, right);
        this.type = type;
        this.identifier = identifier;
        this.lengths = lengths;
        this.values = null;
    }

    // Array con valores
    public SymbolArrayInitialization(SymbolTypeArray type, String identifier, SymbolArrayValues values, Location left, Location right) {
        super("ArrayInitialization", left, right);
        this.type = type;
        this.identifier = identifier;
        this.lengths = null;
        this.values = values;
    }

    public SymbolTypeArray getTypeArray() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public SymbolArrayIndexes getLenghts() {
        return lengths;
    }

    public SymbolArrayValues getValues() {
        return values;
    }

    public boolean isLengthsInitialized() {
        return lengths != null;
    }

    public int getValueDimensions() {
        return isLengthsInitialized() ? lengths.getDimensions() : values.getDimensions();
    }

    public SymbolValue[] getLengthsArray() {
        return isLengthsInitialized() ? lengths.getLengths() : values.getLengths();
    }

    public SymbolType getValueType() {
        return isLengthsInitialized() ? lengths.getType() : values.getType();
    }

    @Override
    public String toString() {
        return String.format("%s %s = %s", type, identifier, isLengthsInitialized() ? lengths : values);
    }
}
