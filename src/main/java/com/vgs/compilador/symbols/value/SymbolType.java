package com.vgs.compilador.symbols.value;

import com.vgs.compilador.symbols.SymbolBase;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolType extends SymbolBase {

    private typesEnum type;

    private SymbolType(typesEnum type, Location left, Location right) {
        super("Type", left, right);
        this.type = type;
    }

    public static SymbolType INTEGER(Location left, Location right) {
        return new SymbolType(typesEnum.Int, left, right);
    }

    public static SymbolType DOUBLE(Location left, Location right) {
        return new SymbolType(typesEnum.Double, left, right);
    }

    public static SymbolType BOOLEAN(Location left, Location right) {
        return new SymbolType(typesEnum.Boolean, left, right);
    }

    public static SymbolType CHARACTER(Location left, Location right) {
        return new SymbolType(typesEnum.Char, left, right);
    }

    public static SymbolType STRING(Location left, Location right) {
        return new SymbolType(typesEnum.String, left, right);
    }

    public static SymbolType VOID(Location left, Location right) {
        return new SymbolType(typesEnum.Void, left, right);
    }

    public typesEnum getType() {
        return this.type;
    }

    public void setType(typesEnum type) {
        this.type = type;
    }

    public boolean isInt() {
        return type == typesEnum.Int;
    }

    public boolean isDouble() {
        return type == typesEnum.Double;
    }

    public boolean isBoolean() {
        return type == typesEnum.Boolean;
    }

    public boolean isChar() {
        return type == typesEnum.Char;
    }

    public boolean isString() {
        return type == typesEnum.String;
    }

    public boolean isVoid() {
        return type == typesEnum.Void;
    }

    public boolean isNumeric() {
        return isInt() || isDouble();
    }

    public boolean isUnaryCompatible() {
        return isNumeric() || isBoolean();
    }

    public enum typesEnum {
        Int, Double, Boolean, Char, String, Void
    }
}
