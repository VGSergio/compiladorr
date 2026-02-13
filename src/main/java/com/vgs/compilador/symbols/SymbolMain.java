package com.vgs.compilador.symbols;

import com.vgs.compilador.symbols.value.SymbolValue;
import com.vgs.compilador.symbols.value.operation.SymbolOperation;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolMain extends SymbolBase {

    private final SymbolValue instructions;

    public SymbolMain(SymbolValue instructions, Location left, Location right) {
        super("Main", left, right);
        this.instructions = instructions;
    }

    public SymbolValue getInstructions() {
        return instructions;
    }
}
