package com.vgs.compilador.symbols.value.operation;

import com.vgs.compilador.manager.ErrorManager;
import com.vgs.compilador.symbols.value.SymbolType;
import com.vgs.compilador.symbols.value.SymbolValue;
import com.vgs.compilador.symbols.value.operation.SymbolOperator.OperatorType;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolOperation extends SymbolValue<SymbolValue> {

    private final SymbolValue firstOperand;
    private final SymbolOperator operator;
    private final SymbolValue secondOperand;
    private final OperationKind kind;

    // Constructor para paréntesis: (value)
    public SymbolOperation(SymbolValue firstOperand, Location left, Location right) {
        super("Operation", left, right);
        this.firstOperand = firstOperand;
        this.operator = null;
        this.secondOperand = null;
        this.kind = OperationKind.PARENTHESIS;
        this.value = this;
        this.type = SymbolType.VOID(left, right);
    }

    // Constructor para operación unaria: -value, !value
    public SymbolOperation(SymbolValue firstOperand, SymbolOperator operator, Location left, Location right) {
        super("Operation", left, right);
        this.firstOperand = firstOperand;
        this.operator = operator;
        this.secondOperand = null;
        this.kind = OperationKind.UNARY;
        this.value = this;
        this.type = SymbolType.VOID(left, right);
    }

    // Constructor para operación binaria: a + b
    public SymbolOperation(SymbolValue firstOperand, SymbolOperator operator, SymbolValue secondOperand, Location left, Location right) {
        super("Operation", left, right);
        this.firstOperand = firstOperand;
        this.operator = operator;
        this.secondOperand = secondOperand;
        this.kind = OperationKind.BINARY;
        this.value = this;
        this.type = SymbolType.VOID(left, right);
    }

    private boolean validateOperation() {
        return kind == OperationKind.UNARY ? validateUnary() : validateBinary();
    }

    private boolean validateUnary() {
        if (!operator.isUnaryCompatible()) {
            return validateOperationError("validateUnary", "Operator %s is not unary compatible", operator);
        }

        SymbolType fot = firstOperand.getType();
        if (!fot.isUnaryCompatible()) {
            return validateOperationError("validateUnary", "Operand %s is not unary compatible", fot);
        }

        OperatorType opType = operator.getOperatorType();
        return switch (opType) {
            case ARITHMETIC ->
                validateNumeric(fot, opType);
            case LOGICAL ->
                validateBoolean(fot);
            default ->
                validateOperationError("validateUnary", "Operator %s cannot be unary", operator);
        };
    }

    private boolean validateBinary() {
        if (!firstOperand.getType().equals(secondOperand.getType())) {
            return validateOperationError("validateBinary", "Type mismatch: %s and %s", firstOperand.getType(), secondOperand.getType());
        }

        SymbolType fot = firstOperand.getType();
        OperatorType opType = operator.getOperatorType();

        return switch (opType) {
            case ARITHMETIC, RELATIONAL ->
                validateNumeric(fot, opType);
            case LOGICAL ->
                validateBoolean(fot);
        };
    }

    private boolean validateNumeric(SymbolType type, OperatorType opType) {
        if (!type.isNumeric()) {
            return validateOperationError("validateNumeric", "%s operator requires numeric operands, got %s", opType, type);
        }
        return true;
    }

    private boolean validateBoolean(SymbolType type) {
        if (!type.isBoolean()) {
            return validateOperationError("validateBoolean", "Logical operator requires boolean operands, got %s", type);
        }
        return true;
    }

    private boolean validateOperationError(String context, String message, Object... args) {
        ErrorManager.semantic(this, String.format("[%s] %s", context, String.format(message, args)));
        return false;
    }

    public void computeOperationType() {
        if (kind == OperationKind.PARENTHESIS) {
            type.setType(firstOperand.getType().getType());
            return;
        }

        if (!validateOperation()) {
            return;
        }

        type.setType(kind == OperationKind.UNARY
                ? firstOperand.getType().getType()
                : inferBinaryResultType());
    }

    private SymbolType.Type inferBinaryResultType() {
        return operator.getOperatorType() == OperatorType.RELATIONAL
                ? SymbolType.Type.BOOLEAN
                : firstOperand.getType().getType();
    }

    private enum OperationKind {
        PARENTHESIS, UNARY, BINARY
    }

    public SymbolValue getFirstOperand() {
        return firstOperand;
    }

    public SymbolOperator getOperator() {
        return operator;
    }

    public SymbolValue getSecondOperand() {
        return secondOperand;
    }

    @Override
    public String toString() {
        return switch (kind) {
            case PARENTHESIS ->
                String.format("(%s)", firstOperand);
            case UNARY ->
                String.format("(%s%s)", operator, firstOperand);
            case BINARY ->
                String.format("(%s %s %s)", firstOperand, operator, secondOperand);
        };
    }

}
