package com.vgs.compilador.symbols.instruction.initialization;

import com.vgs.compilador.symbols.aux.SymbolArrayIndexes;
import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.instruction.SymbolInstruction;
import com.vgs.compilador.symbols.type.SymbolType;
import com.vgs.compilador.symbols.type.SymbolTypeArray;
import com.vgs.compilador.symbols.value.SymbolValue;
import java.util.ArrayList;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolArrayInitialization extends SymbolInstruction {

    private final SymbolTypeArray type;
    private final String identifier;
    private final SymbolArrayIndexes lengths;
    private final SymbolArrayDimension values;

    // Array sin valores
    public SymbolArrayInitialization(SymbolTypeArray type, String identifier, SymbolArrayIndexes lengths, Location left, Location right) {
        super("ArrayInitialization", left, right);
        this.type = type;
        this.identifier = identifier;
        this.lengths = lengths;
        this.values = null;
    }

    // Array sin valores
//    public SymbolArrayInitialization(SymbolTypeArray type, String identifier, ArrayList<SymbolValue<?>> lengths, Location left, Location right) {
//        super("ArrayInitialization", left, right);
//        this.type = type;
//        this.identifier = identifier;
//        this.lengths = lengths;
//        this.values = null;
//    }
    // Array con valores
//    public SymbolArrayInitialization(SymbolTypeArray type, String identifier, Object values, Location left, Location right) {
//        super("ArrayInitialization", left, right);
//        this.type = type;
//        this.identifier = identifier;
//        this.lengths = null;
//        this.values = values;
//    }
    public SymbolTypeArray getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public SymbolArrayIndexes getLenghts() {
        return lengths;
    }
//    public Object getValues() {
//        return values;
//    }

    @Override
    public String toString() {
        return String.format("%s %s", type, identifier);
    }

    public class SymbolArrayDimension extends SymbolValue {

        private final ArrayList<SymbolValue> values;

        public SymbolArrayDimension(Location left, Location right) {
            super("ArrayContent", left, right);
            this.values = new ArrayList<>();
        }

        public void addValue(SymbolValue value) {
            values.add(value);
        }
    }
}
