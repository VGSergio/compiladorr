package com.vgs.compilador.symbols.value;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolFunctionCall<T> extends SymbolValue<T> {

    public SymbolFunctionCall(String name, Location left, Location right) {
        super(name, left, right);
    }

}
