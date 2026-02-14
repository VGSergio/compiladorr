package com.vgs.compilador.semantic;

import com.vgs.compilador.manager.ErrorManager;
import com.vgs.compilador.semantic.entries.SymbolEntry;
import com.vgs.compilador.semantic.entries.SymbolVariableEntry;
import com.vgs.compilador.symbols.SymbolMain;
import com.vgs.compilador.symbols.instruction.SymbolInstruction;
import com.vgs.compilador.symbols.instruction.SymbolInstructions;
import com.vgs.compilador.symbols.instruction.SymbolVariableInitialization;
import com.vgs.compilador.symbols.value.SymbolLiteral;
import com.vgs.compilador.symbols.value.SymbolValue;
import com.vgs.compilador.symbols.value.access.SymbolAccess;
import com.vgs.compilador.symbols.value.access.SymbolArrayAccess;
import com.vgs.compilador.symbols.value.access.SymbolFunctionCall;
import com.vgs.compilador.symbols.value.access.SymbolVariableAccess;
import com.vgs.compilador.symbols.value.operation.SymbolOperation;

/**
 *
 * @author sergi
 */
public class SemanticAnalyzer {

    private SymbolTable symbolTable;

    /**
     * Comprobaciones: 1. No hace falta comprobar nada.
     *
     * @param main
     */
    public void manage(SymbolMain main) {
        symbolTable = new SymbolTable();

        symbolTable.enterBlock(SymbolTable.Scope.ScopeType.TEST);

        manage(main.getInstructions());

        symbolTable.exitBlock();
    }

    /**
     * Comprobaciones: 1. No hace falta comprobar nada.
     */
    private void manage(SymbolInstructions instructions) {
        SymbolInstruction actualInstruction = instructions.getActualInstruction();
        if (actualInstruction != null) {
            manage(actualInstruction);
        }

        SymbolInstructions nextInstructions = instructions.getNextInstructions();
        if (nextInstructions != null) {
            manage(nextInstructions);
        }
    }

    /**
     * Comprobaciones: 1. No hace falta comprobar nada.
     */
    private void manage(SymbolInstruction instruction) {
        switch (instruction) {
            case SymbolVariableInitialization i ->
                manage(i);
            default ->
                ErrorManager.semantic(instruction, String.format("[SymbolInstruction] Unhandled Symbol instance %s", instruction.getClass().getSimpleName()));
        }
    }

    /**
     * Comprobaciones: 1. Comprobar que no exista la variable. 2. Comprobar que
     * el tipo de variable y su valor coinciden.
     */
    private void manage(SymbolVariableInitialization instruction) {
        // 1. Comprobar que no exista la variable.
        if (symbolTable.getDescription(instruction.getIdentifier()) != null) {
            ErrorManager.semantic(instruction, String.format("[SymbolVariableInitialization] Variable: '%s' already exists at this level", instruction.getIdentifier()));
            return;
        }

        SymbolValue value = instruction.getValue();
        if (value != null) {
            if (!manage(value)) {
                return;
            }
            // 2. Comprobar que el tipo de variable y su valor coinciden.
            if (!instruction.getType().equals(instruction.getValue().getType())) {
                ErrorManager.semantic(instruction, String.format("[SymbolVariableInitialization] Type mismatch: %s and %s", instruction.getType(), instruction.getValue().getType()));
                return;
            }
        }

        // Añadimos la variable a la tabla de símbolos
        symbolTable.put(new SymbolVariableEntry(instruction));
    }

    /**
     * Comprobaciones: 1. No hace falta comprobar nada.
     */
    private boolean manage(SymbolValue instruction) {
        switch (instruction) {
            case null -> {
                return true;
            }
            case SymbolLiteral i -> {
                return true;
            }
            case SymbolOperation i -> {
                return manage(i);
            }
            case SymbolAccess i -> {
                return manage(i);
            }
            default -> {
                ErrorManager.semantic(
                        instruction,
                        "[SymbolValue] Unhandled Symbol instance "
                        + instruction.getClass().getSimpleName()
                );
                return false;
            }
        }
    }

    /**
     * Comprobaciones: TODO: 1. Si un operando es una variable comprobar que
     * tenga valor. 2. Comprobar que los operandos son compatibles entre si. 3.
     * Comprobar que el operador sea compatible con los operandos.
     */
    private boolean manage(SymbolOperation instruction) {
        // Calcular tipò de los operandos
        if (!manage(instruction.getFirstOperand())) {
            return false;
        }
        if (!manage(instruction.getSecondOperand())) {
            return false;
        }

        // 2. Comprobar que los operandos son compatibles entre si.
        // 3. Comprobar que el operador sea compatible con los operandos.
        // Asignar a la operación el tipo pertinente
        return instruction.computeOperationType();
    }

    /**
     * Comprobaciones: 1. No hace falta comprobar nada.
     */
    private boolean manage(SymbolAccess instruction) {
        if (!validateAccess(instruction)) {
            return false;
        }
        switch (instruction) {
            case SymbolFunctionCall i -> {
                return manage(i);
            }
            case SymbolArrayAccess i -> {
                return manage(i);
            }
            case SymbolVariableAccess i -> {
                return manage(i);
            }
            default -> {
                ErrorManager.semantic(
                        instruction,
                        "[SymbolAccess] Unhandled Symbol instance "
                        + instruction.getClass().getSimpleName()
                );
                return false;
            }
        }
    }

    private boolean validateAccess(SymbolAccess instruction) {
        if (instruction == null) {
            return false;
        }

        String kind = switch (instruction) {
            case SymbolFunctionCall i ->
                "Function";
            case SymbolArrayAccess i ->
                "Array";
            case SymbolVariableAccess i ->
                "Variable";
            default ->
                null;
        };

        if (kind == null) {
            ErrorManager.semantic(
                    instruction,
                    "[validateAccess] Unhandled Symbol instance "
                    + instruction.getClass().getSimpleName()
            );
            return false;
        }

        String identifier = instruction.getIdentifier();
        if (symbolTable.getDescription(identifier) == null) {
            ErrorManager.semantic(instruction, String.format("[SymbolAccess] %s %s does not exist", kind, identifier));
            return false;
        }

        return true;
    }

    /**
     * Comprobaciones: 1. Comprobar que la variable existe.
     */
    private boolean manage(SymbolFunctionCall instruction) {
        return true;
    }

    /**
     * Comprobaciones: 1. Comprobar que la variable existe.
     */
    private boolean manage(SymbolArrayAccess instruction) {
        return true;
    }

    /**
     * Comprobaciones: 1. Comprobar que la variable existe.
     */
    private boolean manage(SymbolVariableAccess instruction) {
        SymbolVariableEntry entry = (SymbolVariableEntry) symbolTable.getDescription(instruction.getIdentifier());
        if (!entry.hasValue()) {
            ErrorManager.semantic(instruction, String.format("[SymbolVariableAccess] Variable %s value is null", entry.getId()));
            return false;
        }
        instruction.setType(entry.getType());
        instruction.setValue(entry.getValue());
        return true;
    }
}
