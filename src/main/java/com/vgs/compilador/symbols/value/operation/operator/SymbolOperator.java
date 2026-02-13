package com.vgs.compilador.symbols.value.operation.operator;

import com.vgs.compilador.symbols.SymbolBase;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 * @param <T>
 */
public abstract class SymbolOperator<T> extends SymbolBase {

    private final OperationType operationType;
    private final T operator;

    public SymbolOperator(String name, OperationType operationType, T operator, Location left, Location right) {
        super(name, left, right);
        this.operator = operator;
        this.operationType = operationType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public T getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return getSymbol();
    }

    protected abstract String getSymbol();

    public abstract boolean isUnaryCompatible();

    public enum OperationType {
        ARITHMETIC, RELATIONAL, LOGICAL
    }
}
