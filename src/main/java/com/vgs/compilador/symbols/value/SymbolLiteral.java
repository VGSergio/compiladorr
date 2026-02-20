package com.vgs.compilador.symbols.value;

import com.vgs.compilador.symbols.type.SymbolType;

/**
 *
 * @author sergi
 * @param <T>
 */
public class SymbolLiteral<T> extends SymbolValue<T> {

    public SymbolLiteral(SymbolType type, T value) {
        super(type.getType().name(), type, value, type.getLeft(), type.getRight());
    }
}
