package com.vgs.compilador.symbols.value;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 * @param <T>
 */
public class SymbolLiteral<T> extends SymbolValue<T> {

    private SymbolLiteral(SymbolType type, T value, Location left, Location right) {
        super(type.getType().name(), type, value, left, right);
    }

    public static <T> SymbolLiteral<T> create(T value, SymbolType type, Location left, Location right) {
        return new SymbolLiteral<>(type, value, left, right);
    }

    public static SymbolLiteral<Integer> createInteger(Integer value, Location left, Location right) {
        return create(value, SymbolType.INTEGER(left, right), left, right);
    }

    public static SymbolLiteral<Double> createDouble(Double value, Location left, Location right) {
        return create(value, SymbolType.DOUBLE(left, right), left, right);
    }

    public static SymbolLiteral<Boolean> createBoolean(Boolean value, Location left, Location right) {
        return create(value, SymbolType.BOOLEAN(left, right), left, right);
    }

    public static SymbolLiteral<Character> createChar(Character value, Location left, Location right) {
        return create(value, SymbolType.CHARACTER(left, right), left, right);
    }

    public static SymbolLiteral<String> createString(String value, Location left, Location right) {
        return create(value, SymbolType.STRING(left, right), left, right);
    }
}
