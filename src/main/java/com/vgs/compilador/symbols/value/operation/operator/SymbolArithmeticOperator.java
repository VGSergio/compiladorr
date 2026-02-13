package com.vgs.compilador.symbols.value.operation.operator;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolArithmeticOperator extends SymbolOperator<SymbolArithmeticOperator.ArithmeticOperator> {

    private SymbolArithmeticOperator(ArithmeticOperator operator, Location left, Location right) {
        super("ArithmeticOperator", OperationType.ARITHMETIC, operator, left, right);
    }

    public static SymbolArithmeticOperator ADD(Location left, Location right) {
        return new SymbolArithmeticOperator(ArithmeticOperator.ADD, left, right);
    }

    public static SymbolArithmeticOperator SUB(Location left, Location right) {
        return new SymbolArithmeticOperator(ArithmeticOperator.SUB, left, right);
    }

    public static SymbolArithmeticOperator MUL(Location left, Location right) {
        return new SymbolArithmeticOperator(ArithmeticOperator.MUL, left, right);
    }

    public static SymbolArithmeticOperator DIV(Location left, Location right) {
        return new SymbolArithmeticOperator(ArithmeticOperator.DIV, left, right);
    }

    public static SymbolArithmeticOperator MOD(Location left, Location right) {
        return new SymbolArithmeticOperator(ArithmeticOperator.MOD, left, right);
    }

    @Override
    public boolean isUnaryCompatible() {
        return this.getOperator() == ArithmeticOperator.SUB;
    }

    @Override
    protected String getSymbol() {
        return getOperator().getSymbol();
    }

    public enum ArithmeticOperator {
        ADD("+"), SUB("-"), MUL("*"), DIV("/"), MOD("%");

        private final String symbol;

        ArithmeticOperator(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}
