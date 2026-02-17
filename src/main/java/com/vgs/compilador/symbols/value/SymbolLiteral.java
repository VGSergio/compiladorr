package com.vgs.compilador.symbols.value;

import com.vgs.compilador.symbols.type.SymbolType;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 * @param <T>
 */
public class SymbolLiteral<T> extends SymbolValue<T> {

    public SymbolLiteral(SymbolType type, T value, Location left, Location right) {
        super(type.getType().name(), type, value, left, right);
    }
}
