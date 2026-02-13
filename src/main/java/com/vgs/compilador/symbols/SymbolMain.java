package com.vgs.compilador.symbols;

import com.vgs.compilador.symbols.instruction.SymbolInstructions;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolMain extends SymbolBase {

    private final SymbolInstructions instructions;

    public SymbolMain(SymbolInstructions instructions, Location left, Location right) {
        super("Main", left, right);
        this.instructions = instructions;
    }

    public SymbolInstructions getInstructions() {
        return instructions;
    }
}
