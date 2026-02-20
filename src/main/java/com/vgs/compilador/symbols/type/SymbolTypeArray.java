package com.vgs.compilador.symbols.type;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolTypeArray extends SymbolType {

    private final int nDims;

    public SymbolTypeArray(SymbolType type, int nDims, Location left, Location right) {
        super(type.getType(), left, right);
        this.nDims = nDims;
    }

    public int getNDims() {
        return nDims;
    }
    
    @Override
    public String toString(){
        return super.toString() + "[]".repeat(nDims);
    }
}
