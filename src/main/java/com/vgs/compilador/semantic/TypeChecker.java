package com.vgs.compilador.semantic;

import com.vgs.compilador.manager.ErrorManager;
import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.SymbolMain;
import com.vgs.compilador.symbols.instruction.SymbolInstruction;
import com.vgs.compilador.symbols.instruction.SymbolInstructions;
import com.vgs.compilador.symbols.value.SymbolLiteral;
import com.vgs.compilador.symbols.value.SymbolValue;
import com.vgs.compilador.symbols.value.operation.SymbolOperation;

/**
 *
 * @author sergi
 */
public class TypeChecker {

    /**
     * Comprobaciones: 1. No hace falta comprobar nada.
     *
     * @param main
     */
    public void manage(SymbolMain main) {
//        symbolTable = new SymbolTable();

        for (SymbolInstructions instructions = main.getInstructions(); instructions != null; instructions = instructions.getNextInstructions()) {
            SymbolInstruction actualInstruction = instructions.getActualInstruction();
            manage(actualInstruction.getInstruction());
//            manage(actualInstruction);
        }
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

//            case SymbolVariableInitialization i -> manage(i);
//            case SymbolAssignValue i -> manage(i);
//            case SymbolIf i -> manage(i);
//            case SymbolSwitch i -> manage(i);
//            case SymbolFor i -> manage(i);
//            case SymbolWhile i -> manage(i);
//            case SymbolFunction i -> manage(i);
//            case SymbolFunctionCall i -> manage(i);
//            case SymbolInput i -> manage(i);
//            case SymbolPrint i -> manage(i);
//            case SymbolBreak i -> manage(i);
            case SymbolBase i ->
                manage(i);
//            default ->
//                ErrorManager.semantic(instruction, String.format("[SymbolInstruction] Unhandled Symbol instance %s", instruction.getClass().getSimpleName()));
        }
    }

    /**
     * Comprobaciones: 1. No hace falta comprobar nada.
     */
    private void manage(SymbolBase instruction) {
        switch (instruction) {
//            case SymbolVariableInitialization i -> manage(i);
//            case SymbolAssignValue i -> manage(i);
//            case SymbolIf i -> manage(i);
//            case SymbolSwitch i -> manage(i);
//            case SymbolFor i -> manage(i);
//            case SymbolWhile i -> manage(i);
//            case SymbolFunction i -> manage(i);
//            case SymbolFunctionCall i -> manage(i);
//            case SymbolInput i -> manage(i);
//            case SymbolPrint i -> manage(i);
//            case SymbolBreak i -> manage(i);
            case SymbolOperation i ->
                manage(i);
            default ->
                ErrorManager.semantic(instruction, String.format("[SymbolBase] Unhandled Symbol instance %s", instruction.getClass().getSimpleName()));
        }
    }

    /**
     * Comprobaciones: 1. No hace falta comprobar nada.
     */
    private void manage(SymbolValue instruction) {
        switch (instruction) {
            case null -> {
            }

//            case SymbolVariableInitialization i -> manage(i);
//            case SymbolAssignValue i -> manage(i);
//            case SymbolIf i -> manage(i);
//            case SymbolSwitch i -> manage(i);
//            case SymbolFor i -> manage(i);
//            case SymbolWhile i -> manage(i);
//            case SymbolFunction i -> manage(i);
//            case SymbolFunctionCall i -> manage(i);
//            case SymbolInput i -> manage(i);
//            case SymbolPrint i -> manage(i);
//            case SymbolBreak i -> manage(i);
            case SymbolLiteral i ->
                manage(i);
            case SymbolOperation i ->
                manage(i);
            default ->
                ErrorManager.semantic(instruction, String.format("[SymbolValue] Unhandled Symbol instance %s", instruction.getClass().getSimpleName()));
        }
    }

    /**
     * Comprobaciones: 1. Comprobar que los operandos son compatibles entre si.
     * 2. Comprobar que el operador sea compatible con los operandos.
     */
    private void manage(SymbolOperation instruction) {
        manage(instruction.getFirstOperand());
        manage(instruction.getSecondOperand());

        // 1. Comprobar que los operandos son compatibles entre si.
        // 2. Comprobar que el operador sea compatible con los operandos.
        // Asignamos a la operación el tipo pertinente
        instruction.computeOperationType();
    }

    /**
     * Comprobaciones: 1. No hace falta comprobar nada.
     */
    private void manage(SymbolLiteral instruction) {
    }
}
