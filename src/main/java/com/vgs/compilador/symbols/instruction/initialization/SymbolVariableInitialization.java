package com.vgs.compilador.symbols.instruction;

import com.vgs.compilador.symbols.type.SymbolType;
import com.vgs.compilador.symbols.value.SymbolValue;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolVariableInitialization extends SymbolInstruction {

    private final boolean isFinal;
    private final SymbolType type;
    private final String identifier;

    // Constructor sin valor
    public SymbolVariableInitialization(SymbolType type, String identifier, Location left, Location right) {
        super("VariableInitialization", left, right);
        this.isFinal = false;
        this.type = type;
        this.identifier = identifier;
        this.value = null;
    }

    public SymbolVariableInitialization(boolean isFinal, SymbolType type, String identifier, SymbolValue value, Location left, Location right) {
        super("VariableInitialization", left, right);
        this.isFinal = isFinal;
        this.type = type;
        this.identifier = identifier;
        this.value = value;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public SymbolType getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public SymbolValue<?> getValue() {
        return (SymbolValue<?>) value;
    }

    public boolean hasValue() {
        return value != null;
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("%s %s = %s;", type, identifier, value)
                : String.format("%s %s;", type, identifier);
    }
}
