package com.vgs.compilador.symbols.instruction.function;

import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.type.SymbolType;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolParameter extends SymbolBase {

    private final SymbolType type;
    private final String identifier;

    public SymbolParameter(SymbolType type, String identifier, Location left, Location right) {
        super("Parameter", left, right);
        this.type = type;
        this.identifier = identifier;
    }

    public SymbolType getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return type + " " + identifier;
    }
}
