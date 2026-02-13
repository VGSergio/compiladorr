package com.vgs.compilador.symbols.instruction;

import com.vgs.compilador.symbols.SymbolBase;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolInstruction extends SymbolBase {

    private SymbolBase instruction;

    public SymbolInstruction(SymbolBase instruction, Location left, Location right) {
        super("Instruction", left, right);
        this.instruction = instruction;
    }
}
