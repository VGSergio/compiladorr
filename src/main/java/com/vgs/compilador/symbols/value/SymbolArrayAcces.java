package com.vgs.compilador.symbols.value;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolArrayAcces<T> extends SymbolValue<T> {
    
    public SymbolArrayAcces(String name, Location left, Location right) {
        super(name, left, right);
    }

}
