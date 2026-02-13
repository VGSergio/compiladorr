package com.vgs.compilador.symbols.value;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolLiteral<T> extends SymbolValue<T> {

    private SymbolLiteral(String name, SymbolType type, T value, Location left, Location right) {
        super(name, type, value, left, right);
    }

    public static SymbolLiteral<Integer> createInteger(Integer value, Location left, Location right) {
        return new SymbolLiteral<>("Integer", SymbolType.INTEGER(left, right), value, left, right);
    }

    public static SymbolLiteral<Double> createDouble(Double value, Location left, Location right) {
        return new SymbolLiteral<>("Double", SymbolType.DOUBLE(left, right), value, left, right);
    }

    public static SymbolLiteral<Boolean> createBoolean(Boolean value, Location left, Location right) {
        return new SymbolLiteral<>("Boolean", SymbolType.BOOLEAN(left, right), value, left, right);
    }

    public static SymbolLiteral<Character> createChar(Character value, Location left, Location right) {
        return new SymbolLiteral<>("Character", SymbolType.CHARACTER(left, right), value, left, right);
    }

    public static SymbolLiteral<String> createString(String value, Location left, Location right) {
        return new SymbolLiteral<>("String", SymbolType.STRING(left, right), value, left, right);
    }
}
