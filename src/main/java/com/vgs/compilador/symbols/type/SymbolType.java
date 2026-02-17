package com.vgs.compilador.symbols.type;

import com.vgs.compilador.symbols.SymbolBase;
import java.util.Objects;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolType extends SymbolBase {

    private Type type;

    protected SymbolType(Type type, Location left, Location right) {
        super("Type", left, right);
        this.type = type;
    }

    public static SymbolType INTEGER(Location left, Location right) {
        return new SymbolType(Type.INTEGER, left, right);
    }

    public static SymbolType DOUBLE(Location left, Location right) {
        return new SymbolType(Type.DOUBLE, left, right);
    }

    public static SymbolType BOOLEAN(Location left, Location right) {
        return new SymbolType(Type.BOOLEAN, left, right);
    }

    public static SymbolType CHARACTER(Location left, Location right) {
        return new SymbolType(Type.CHARACTER, left, right);
    }

    public static SymbolType STRING(Location left, Location right) {
        return new SymbolType(Type.STRING, left, right);
    }

    public static SymbolType VOID(Location left, Location right) {
        return new SymbolType(Type.VOID, left, right);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isInt() {
        return type == Type.INTEGER;
    }

    public boolean isDouble() {
        return type == Type.DOUBLE;
    }

    public boolean isBoolean() {
        return type == Type.BOOLEAN;
    }

    public boolean isChar() {
        return type == Type.CHARACTER;
    }

    public boolean isString() {
        return type == Type.STRING;
    }

    public boolean isVoid() {
        return type == Type.VOID;
    }

    public boolean isNumeric() {
        return switch (type) {
            case INTEGER, DOUBLE ->
                true;
            default ->
                false;
        };
    }

    public boolean isUnaryCompatible() {
        return switch (type) {
            case INTEGER, DOUBLE, BOOLEAN ->
                true;
            default ->
                false;
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SymbolType other)) {
            return false;
        }
        return this.type == other.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }

    @Override
    public String toString() {
        return switch (type) {
            case INTEGER ->
                "int";
            case DOUBLE ->
                "double";
            case BOOLEAN ->
                "boolean";
            case CHARACTER ->
                "char";
            case STRING ->
                "String";
            case VOID ->
                "void";
        };
    }

    public enum Type {
        INTEGER, DOUBLE, BOOLEAN, CHARACTER, STRING, VOID
    }
}
