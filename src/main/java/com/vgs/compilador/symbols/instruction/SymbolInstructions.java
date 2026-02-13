package com.vgs.compilador.symbols.instruction;

import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.value.SymbolValue;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolInstructions extends SymbolBase {

    private final SymbolInstruction actualInstruction;
    private final SymbolInstructions nextInstructions;

    public SymbolInstructions() {
        super("Instructions");
        this.actualInstruction = null;
        this.nextInstructions = null;
    }

    public SymbolInstructions(SymbolInstruction actualInstruction, SymbolInstructions nextInstructions, Location left, Location right) {
        super("Instructions", left, right);
        this.actualInstruction = actualInstruction;
        this.nextInstructions = nextInstructions;
    }

    public SymbolInstruction getActualInstruction() {
        return actualInstruction;
    }

    public SymbolInstructions getNextInstructions() {
        return nextInstructions;
    }

}
