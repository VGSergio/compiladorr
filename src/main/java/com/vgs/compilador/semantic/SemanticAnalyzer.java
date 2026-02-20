package com.vgs.compilador.semantic;

import com.vgs.compilador.manager.ErrorManager;
import com.vgs.compilador.semantic.entries.SymbolArrayEntry;
import com.vgs.compilador.semantic.entries.SymbolEntry;
import com.vgs.compilador.semantic.entries.SymbolVariableEntry;
import com.vgs.compilador.semantic.entries.SymbolFunctionEntry;
import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.SymbolMain;
import com.vgs.compilador.symbols.helpers.SymbolArrayIndexes;
import com.vgs.compilador.symbols.helpers.SymbolArrayValues;
import com.vgs.compilador.symbols.instruction.initialization.SymbolArrayInitialization;
import com.vgs.compilador.symbols.instruction.SymbolInstruction;
import com.vgs.compilador.symbols.instruction.SymbolInstructions;
import com.vgs.compilador.symbols.instruction.initialization.SymbolVariableInitialization;
import com.vgs.compilador.symbols.type.SymbolType;
import com.vgs.compilador.symbols.value.SymbolLiteral;
import com.vgs.compilador.symbols.type.SymbolTypeArray;
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
            case SymbolArrayInitialization i ->
                manage(i);
            default ->
                ErrorManager.semantic(instruction, String.format("[SymbolInstruction] Unhandled Symbol instance %s", instruction.getClass().getSimpleName()));
        }
    }

    /**
     * Comprobaciones: 1. Comprobar que el identificador este disponible. 2.
     * Comprobar que el tipo de variable y su valor coinciden.
     */
    private void manage(SymbolVariableInitialization instruction) {
        // 1. Comprobar que el identificador este disponible.
        if (isIdentifierInUse(instruction, instruction.getIdentifier())) {
            return;
        }

        SymbolValue value = instruction.getValue();
        if (value != null) {
            if (!manage(value)) {
                return;
            }

            // 2. Comprobar que el tipo de variable y su valor coinciden.
            if (!validateType(instruction, instruction.getType(), value.getType())) {
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
            case SymbolArrayValues i -> {
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

        return switch (instruction) {
            case SymbolVariableAccess i ->
                validateEntry(i, SymbolVariableEntry.class, "Variable");
            case SymbolArrayAccess i ->
                validateEntry(i, SymbolArrayEntry.class, "Array");
            case SymbolFunctionCall i ->
                validateEntry(i, SymbolFunctionEntry.class, "Function");
            default -> {
                ErrorManager.semantic(instruction,
                        "[validateAccess] Unhandled Symbol instance: " + instruction.getClass().getSimpleName());
                yield false;
            }
        };
    }

    private <T extends SymbolEntry> boolean validateEntry(SymbolAccess instruction, Class<T> expectedClass, String kindName) {
        String identifier = instruction.getIdentifier();
        SymbolEntry entry = symbolTable.getDescription(identifier);

        if (entry == null) {
            ErrorManager.semantic(instruction, String.format("[SymbolAccess] %s '%s' does not exist", kindName, identifier));
            return false;
        }

        if (!expectedClass.isInstance(entry)) {
            String actualKind = getEntryKind(entry);
            ErrorManager.semantic(instruction, String.format(
                    "[SymbolAccess] Invalid %s access for %s '%s'",
                    kindName.toLowerCase(), actualKind.toLowerCase(), identifier)
            );
            return false;
        }

        return true;
    }

    private String getEntryKind(SymbolEntry entry) {
        return switch (entry) {
            case SymbolFunctionEntry f ->
                "Function";
            case SymbolArrayEntry a ->
                "Array";
            case SymbolVariableEntry v ->
                "Variable";
            default ->
                "Unknown";
        };
    }

    /**
     * Comprobaciones: 1. Comprobar que la variable este inicializada.
     */
    private boolean manage(SymbolVariableAccess instruction) {
        SymbolVariableEntry entry = (SymbolVariableEntry) symbolTable.getDescription(instruction.getIdentifier());

        // 1. Comprobar que la variable este inicializada.
        if (!entry.hasValue()) {
            ErrorManager.semantic(instruction, String.format("[SymbolVariableAccess] Variable %s value is null", entry.getId()));
            return false;
        }

        instruction.setType(entry.getType());
        instruction.setValue(entry.getValue());
        return true;
    }

    /**
     * Comprobaciones: 1. Comprobar que las dimensiones coinciden.
     */
    private boolean manage(SymbolArrayAccess instruction) {
        SymbolArrayEntry description = (SymbolArrayEntry) symbolTable.getDescription(instruction.getIdentifier());
        // 1. Comprobar que las dimensiones coinciden.
        if (!validateDimensions(instruction, description.getDimensions(), instruction.getDimensions())) {
            return false;
        }

        instruction.setType(description.getType());
        return true;
    }

    /**
     * Comprobaciones: 1. Comprobar que el número de parametros coincide.
     */
    private boolean manage(SymbolFunctionCall instruction) {
        return false;
    }

    /**
     * Comprobaciones: 1. Comprobar que el identificador este disponible. 2.
     * Comprobar que el tipo coincide. 3. Comprobar que las dimensiones
     * coinciden.
     */
    private void manage(SymbolArrayInitialization instruction) {
        String id = instruction.getIdentifier();

        // 1. Comprobar que el identificador este disponible.
        if (isIdentifierInUse(instruction, id)) {
            return;
        }

        SymbolArrayIndexes lengths = instruction.getLenghts();
        if ((lengths != null && !manage(lengths))
                || (lengths == null && !manage(instruction.getValues()))) {
            return;
        }

        SymbolTypeArray type = instruction.getTypeArray();
        int valueDims = instruction.getValueDimensions();

        // 1. Comprobar que el tipo coincide.
        if (!validateType(instruction, type, instruction.getValueType())) {
            return;
        }

        // 2. Comprobar que las dimensiones coinciden.
        if (!validateDimensions(instruction, type.getNDims(), valueDims)) {
            return;
        }

        SymbolArrayEntry entry = new SymbolArrayEntry(id, type, instruction, valueDims, instruction.getLengthsArray());
        symbolTable.put(entry);
    }

    /**
     * Comprobaciones: 1. Comprobar que el valor sea de tipo entero.
     */
    private boolean manage(SymbolArrayIndexes instruction) {
        for (SymbolValue length : instruction.getLengths()) {
            if (!manage(length)) {
                return false;
            }
            // 1. Comprobar que el valor sea de tipo entero.
            if (!length.getType().isInt()) {
                ErrorManager.semantic(instruction, String.format("[SymbolArrayLengths] Array length must be integer, found %s", length.getType()));
                return false;
            }
        }
        return true;
    }

    /**
     * Comprobaciones: 1. Comprobar que si un valor es una dimension el resto
     * también lo sean. 2. Comprobar que todos los valores son del mismo tipo.
     * 3. Comprobar que la profundidad de las dimensiones internas coinciden. 4.
     * Comprobar que la longitud de las dimensiones internas coinciden.
     */
    private boolean manage(SymbolArrayValues instruction) {
        SymbolValue[] values = instruction.getValues();

        SymbolValue first = values[0];
        if (!manage(first)) {
            return false;
        }

        SymbolType firstType = first.getType();
        boolean firstIsDim = false;
        int firstDims = 0;
        int firstLength = 0;
        if (first instanceof SymbolArrayValues v1) {
            firstIsDim = true;
            firstDims = v1.getDimensions();
            firstLength = v1.getLength();
        }

        for (int i = 1; i < values.length; i++) {
            SymbolValue value = values[i];
            if (!manage(value)) {
                return false;
            }

            boolean valueIsDim = value instanceof SymbolArrayValues;

            // 1. Comprobar que si un valor es una dimension el resto también lo sean
            if (firstIsDim != valueIsDim) {
                ErrorManager.semantic(instruction, "[SymbolArrayValues] Mixed scalar and array values are not allowed");
                return false;
            }

            // 2. Comprobar que todos los valores son del mismo tipo.
            if (!validateType(instruction, firstType, value.getType())) {
                return false;
            }

            if (firstIsDim) {
                SymbolArrayValues valueDim = (SymbolArrayValues) value;

                // 3. Comprobar que la profundidad de las dimensiones internas coinciden.
                if (firstDims != valueDim.getDimensions()) {
                    ErrorManager.semantic(instruction, String.format("[SymbolArrayValues] Depth mismatch %s != %s", firstDims, valueDim.getDimensions()));
                    return false;
                }

                // 4. Comprobar que la longitud de las dimensiones internas coinciden.
                if (firstLength != valueDim.getLength()) {
                    ErrorManager.semantic(instruction, String.format("[SymbolArrayValues] Length mismatch %s != %s", firstLength, valueDim.getLength()));
                    return false;
                }
            }
        }

        instruction.setType(firstType);
        return true;
    }

    private boolean validateDimensions(SymbolBase instruction, int first, int second) {
        if (first != second) {
            ErrorManager.semantic(instruction, String.format("[%s] Dimensions mismatch: %s and %s", instruction.getClass().getSimpleName(), first, second));
            return false;
        }
        return true;
    }

    private boolean validateType(SymbolBase instruction, SymbolType first, SymbolType second) {
        if (!first.equals(second)) {
            ErrorManager.semantic(instruction, String.format("[%s] Type mismatch: %s and %s", instruction.getClass().getSimpleName(), first, second));
            return false;
        }
        return true;
    }

    private boolean isIdentifierInUse(SymbolBase instruction, String identifier) {
        if (symbolTable.getDescription(identifier) != null) {
            ErrorManager.semantic(instruction, String.format("[%s] Identifier '%s' already in use", instruction.getClass().getSimpleName(), identifier));
            return true;
        }
        return false;
    }
}
