package com.vgs.compilador.symbols.value.operation;

import com.vgs.compilador.manager.ErrorManager;
import com.vgs.compilador.symbols.value.SymbolType;
import com.vgs.compilador.symbols.value.SymbolValue;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolOperation extends SymbolValue<SymbolValue> {

    private final SymbolValue firstOperand;
    private final SymbolOperator operator;
    private final SymbolValue secondOperand;

    // (value)
    public SymbolOperation(SymbolValue firstOperand, Location left, Location right) {
        super("Operation", left, right);
        this.firstOperand = firstOperand;
        this.operator = null;
        this.secondOperand = null;
        this.value = this;
        this.type = SymbolType.VOID(left, right);
    }

    // Unary operation
    public SymbolOperation(SymbolValue firstOperand, SymbolOperator operator, Location left, Location right) {
        super("Operation", left, right);
        this.firstOperand = firstOperand;
        this.operator = operator;
        this.secondOperand = null;
        this.value = this;
        this.type = SymbolType.VOID(left, right);
    }

    // Operation
    public SymbolOperation(SymbolValue firstOperand, SymbolOperator operator, SymbolValue secondOperand, Location left, Location right) {
        super("Operation", left, right);
        this.firstOperand = firstOperand;
        this.operator = operator;
        this.secondOperand = secondOperand;
        this.value = this;
        this.type = SymbolType.VOID(left, right);
    }

    private boolean validateOperation() {
        if (isUnaryOperation()) {
            return validateUnaryOperation();
        }

        if (!firstOperand.getType().equals(secondOperand.getType())) {
            addSemanticError(String.format("[validateOperation] First operand %s and second operand %s are notcompatible", firstOperand, secondOperand));
            return false;
        }

        SymbolType fot = firstOperand.getType();
        switch (operator.getOperatorType()) {
            case ARITHMETIC: {
                if (!fot.isNumeric()) {
                    addSemanticError(String.format("[validateOperation] Operator %s not compatible with operands %s and ", operator, firstOperand, secondOperand));
                    return false;
                }
            }
            case LOGICAL: {
                if (!fot.isBoolean()) {
                    addSemanticError(String.format("[validateOperation] Operator %s not compatible with operands %s and ", operator, firstOperand, secondOperand));
                    return false;
                }
            }
            case RELATIONAL: {
                if (!fot.isNumeric()) {
                    addSemanticError(String.format("[validateOperation] Operator %s not compatible with operands %s and ", operator, firstOperand, secondOperand));
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isOperation() {
        return operator == null && secondOperand == null;
    }

    private boolean isUnaryOperation() {
        return secondOperand == null;
    }

    private boolean validateUnaryOperation() {
        if (!operator.isUnaryCompatible()) {
            addSemanticError(String.format("[validateUnaryOperation] Operator %s is not unary compatible", operator));
            return false;
        }

        SymbolType fot = firstOperand.getType();

        if (!fot.isUnaryCompatible()) {
            addSemanticError(String.format("[validateUnaryOperation] Operand %s is not unary compatible", firstOperand));
            return false;
        }

        switch (operator.getOperatorType()) {
            case ARITHMETIC: {
                if (!fot.isNumeric()) {
                    addSemanticError(String.format("[validateUnaryOperation] Operator %s not compatible with operand %s", operator, firstOperand));
                    return false;
                }
            }
            case LOGICAL: {
                if (!fot.isBoolean()) {
                    addSemanticError(String.format("[validateUnaryOperation] Operator %s not compatible with operand %s", operator, firstOperand));
                    return false;
                }
            }
        }

        return true;
    }

    public void computeOperationType() {
        if (!isOperation()) {  // Solo un operando (paréntesis)
            this.type.setType(firstOperand.getType().getType());
            return;
        }

        if (!validateOperation()) {
            return;
        }
        SymbolType.typesEnum fot = firstOperand.getType().getType();

        switch (operator.getOperatorType()) {
            case ARITHMETIC, LOGICAL -> this.type.setType(fot);
            case RELATIONAL -> this.type.setType(SymbolType.typesEnum.Boolean);
        }
    }

    private void addSemanticError(String message) {
        ErrorManager.semantic(this, message);
    }
}
