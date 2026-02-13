package com.vgs.compilador.symbols.instruction;

import com.vgs.compilador.symbols.SymbolBase;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolInstructions extends SymbolBase {

    public SymbolInstructions(String name, Location left, Location right) {
        super(name, left, right);
    }

}
