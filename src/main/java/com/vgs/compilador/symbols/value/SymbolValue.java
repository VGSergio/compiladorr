package com.vgs.compilador.symbols.value;

import com.vgs.compilador.symbols.SymbolBase;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 * @param <T>
 */
public abstract class SymbolValue<T> extends SymbolBase {

    protected SymbolType type;

    // Operaciones y acceso a variables
    public SymbolValue(String name, Location left, Location right) {
        super(name, left, right);
    }

    // Literal
    public SymbolValue(String name, SymbolType type, T value, Location left, Location right) {
        super(name, left, right, value);
        this.type = type;
    }

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }

    public T getValue() {
        return (T) value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ((T) value).toString();
    }
}
