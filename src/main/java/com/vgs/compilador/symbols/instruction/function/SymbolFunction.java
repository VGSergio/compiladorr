package com.vgs.compilador.symbols.instruction.function;

import com.vgs.compilador.symbols.instruction.SymbolInstruction;
import com.vgs.compilador.symbols.instruction.SymbolInstructions;
import com.vgs.compilador.symbols.type.SymbolType;
import java.util.ArrayList;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolFunction extends SymbolInstruction {

    private final SymbolType type;
    private final String identifier;
    private final ArrayList<SymbolParameter> parameters;
    private final SymbolInstructions instructions;

    public SymbolFunction(SymbolType type, String identifier, ArrayList<SymbolParameter> parameters, SymbolInstructions instructions, Location left, Location right) {
        super("Function", left, right);
        this.type = type;
        this.identifier = identifier;
        this.parameters = !parameters.isEmpty() ? parameters : null;
        this.instructions = instructions != null ? instructions : null;
    }

    public SymbolType getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ArrayList<SymbolParameter> getParameters() {
        return parameters;
    }

    public SymbolInstructions getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return String.format("%s %s(%s){%s}", type, identifier, paramsToString(), instructionsToString());
    }

    private String paramsToString() {
        return parameters != null
                ? String.join(", ", parameters.stream().map(Object::toString).toArray(String[]::new))
                : "";
    }

    private String instructionsToString() {
        return instructions != null
                ? "\n" + instructions + "\n"
                : "";
    }
}
