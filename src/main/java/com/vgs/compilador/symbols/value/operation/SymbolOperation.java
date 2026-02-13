package com.vgs.compilador.symbols.value.operation;

import com.vgs.compilador.manager.ErrorManager;
import com.vgs.compilador.symbols.value.SymbolType;
import com.vgs.compilador.symbols.value.SymbolValue;
import com.vgs.compilador.symbols.value.operation.operator.SymbolArithmeticOperator;
import com.vgs.compilador.symbols.value.operation.operator.SymbolLogicalOperator;
import com.vgs.compilador.symbols.value.operation.operator.SymbolOperator;
import static com.vgs.compilador.symbols.value.operation.operator.SymbolOperator.OperationType.ARITHMETIC;
import static com.vgs.compilador.symbols.value.operation.operator.SymbolOperator.OperationType.LOGICAL;
import static com.vgs.compilador.symbols.value.operation.operator.SymbolOperator.OperationType.RELATIONAL;
import com.vgs.compilador.symbols.value.operation.operator.SymbolRelationalOperator;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolOperation extends SymbolValue<SymbolValue> {

    private final SymbolValue firstOperand;
    private final SymbolOperator operator;
    private final SymbolValue secondOperand;

    public SymbolOperation(SymbolValue firstOperand, Location left, Location right) {
        super("Operation", left, right);
        this.firstOperand = firstOperand;
        this.operator = null;
        this.secondOperand = null;
        this.value = this;
        this.type = SymbolType.VOID(left, right);
    }

    private SymbolOperation(SymbolValue firstOperand, SymbolOperator<?> operator, Location left, Location right) {
        super("Operation", left, right);
        this.firstOperand = firstOperand;
        this.operator = operator;
        this.secondOperand = null;
        this.value = this;
        this.type = SymbolType.VOID(left, right);
    }

    private SymbolOperation(SymbolValue firstOperand, SymbolOperator<?> operator, SymbolValue secondOperand, Location left, Location right) {
        super("Operation", left, right);
        this.firstOperand = firstOperand;
        this.operator = operator;
        this.secondOperand = secondOperand;
        this.value = this;
        this.type = SymbolType.VOID(left, right);
    }

    public static SymbolOperation createOperation(SymbolValue firstOperand, SymbolArithmeticOperator operator, SymbolValue secondOperand, Location left, Location right) {
        return new SymbolOperation(firstOperand, operator, secondOperand, left, right);
    }

    public static SymbolOperation createOperation(SymbolValue firstOperand, SymbolRelationalOperator operator, SymbolValue secondOperand, Location left, Location right) {
        return new SymbolOperation(firstOperand, operator, secondOperand, left, right);
    }

    public static SymbolOperation createOperation(SymbolValue firstOperand, SymbolLogicalOperator operator, SymbolValue secondOperand, Location left, Location right) {
        return new SymbolOperation(firstOperand, operator, secondOperand, left, right);
    }

    public static SymbolOperation createOperation(SymbolOperation firstOperand, SymbolOperator<?> operator, Location left, Location right) {
        if (!operator.isUnaryCompatible()) {
            ErrorManager.semantic(firstOperand, String.format("Invalid Operator Type: %s for unary operation", operator.getOperationType()));
            return null;
        }
        return new SymbolOperation(firstOperand, operator, left, right);
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
        switch (operator.getOperationType()) {
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

        switch (operator.getOperationType()) {
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
        SymbolType.typesEnum fot = firstOperand.getType().getType();
        if (!isOperation()) {
            this.type.setType(fot);
            return;
        }
        
        if (!validateOperation()) {
            return;
        }

        switch (operator.getOperationType()) {
            case ARITHMETIC ->
                this.type.setType(fot);
            case LOGICAL ->
                this.type.setType(fot);
            case RELATIONAL ->
                this.type.setType(SymbolType.typesEnum.Boolean);
        }
    }

    private void addSemanticError(String message) {
        ErrorManager.semantic(this, message);
    }
}
