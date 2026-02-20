package com.vgs.compilador.semantic.entries;

import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.type.SymbolTypeArray;
import com.vgs.compilador.symbols.value.SymbolValue;

/**
 *
 * @author sergi
 */
public class SymbolArrayEntry extends SymbolEntry {

    private final int dims;
    private final SymbolValue[] lengths;        // Lengths de cada dimensión

    public SymbolArrayEntry(String name, SymbolTypeArray arrayType, SymbolBase declaration, int dims, SymbolValue[] lengths) {
        super(name, arrayType, EntryKind.ARRAY, declaration);
        this.dims = dims;
        this.lengths = lengths.clone();
    }

    public int getDimensions() {
        return dims;
    }

    @Override
    public SymbolTypeArray getType() {
        return (SymbolTypeArray) type;
    }
}
