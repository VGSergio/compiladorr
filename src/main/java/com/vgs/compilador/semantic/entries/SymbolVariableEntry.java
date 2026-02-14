package com.vgs.compilador.semantic.entries;

import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.instruction.SymbolVariableInitialization;
import com.vgs.compilador.symbols.value.SymbolType;
import com.vgs.compilador.symbols.value.SymbolValue;

/**
 *
 * @author sergi
 */
public class SymbolVariableEntry extends SymbolEntry {

    private SymbolValue<?> value;  // Valor actual (para constantes o optimización)
    private final boolean isConstant;

    public SymbolVariableEntry(SymbolVariableInitialization variable) {
        super(variable.getIdentifier(), variable.getType(), EntryKind.VARIABLE, variable);
        this.value = variable.getValue();
        this.isConstant = variable.isFinal();
    }

    public SymbolValue<?> getValue() {
        return value;
    }

    public void setValue(SymbolValue<?> value) {
        if (!isConstant) {
            this.value = value;
        }
    }

    public boolean isConstant() {
        return isConstant;
    }

    public boolean hasValue() {
        return value != null;
    }

    @Override
    public String toString() {
        if (value != null) {
            return super.toString() + " = " + value;
        }
        return super.toString();
    }
}
