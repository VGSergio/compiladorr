package com.vgs.compilador.symbols.instruction.function;

import com.vgs.compilador.symbols.instruction.SymbolInstruction;
import com.vgs.compilador.symbols.value.SymbolValue;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolReturn extends SymbolInstruction {

    public SymbolReturn(Location left, Location right) {
        super("Return", left, right);
        this.value = null;
    }

    public SymbolReturn(SymbolValue<?> value, Location left, Location right) {
        super("Return", left, right);
        this.value = value;
    }

    public SymbolValue<?> getValue() {
        return (SymbolValue<?>) value;
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("return %s;", value)
                : "return;";
    }

}
