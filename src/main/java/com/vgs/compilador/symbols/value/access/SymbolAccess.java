package com.vgs.compilador.symbols.value.access;

import com.vgs.compilador.symbols.value.SymbolType;
import com.vgs.compilador.symbols.value.SymbolValue;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public abstract class SymbolAccess extends SymbolValue<Object> {

    protected final String identifier;

    public SymbolAccess(String name, String identifier, Location left, Location right) {
        super(name, left, right);
        this.identifier = identifier;
        this.type = SymbolType.VOID(left, right);
        this.value = this;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public abstract String toString();
}
