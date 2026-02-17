package com.vgs.compilador.symbols.instruction;

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

//    private ArrayList<SymbolValue<?>> lengths;
//    private Object values;
    
    public SymbolArrayInitialization(SymbolTypeArray type, String identifier, Location left, Location right) {
        super("ArrayInitialization", left, right);
        this.type = type;
        this.identifier = identifier;
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

//    public ArrayList<SymbolValue<?>> getLenghts() {
//        return lengths;
//    }
//    public Object getValues() {
//        return values;
//    }
    
    @Override
    public String toString() {
        return String.format("%s %s", type, identifier);
    }
}
