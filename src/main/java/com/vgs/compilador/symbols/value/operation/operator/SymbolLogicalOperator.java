package com.vgs.compilador.symbols.value.operation.operator;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolLogicalOperator extends SymbolOperator<SymbolLogicalOperator.LogicalOperator> {

    private SymbolLogicalOperator(LogicalOperator type, Location left, Location right) {
        super("LogicalOperator", OperationType.LOGICAL, type, left, right);
    }

    public static SymbolLogicalOperator AND(Location left, Location right) {
        return new SymbolLogicalOperator(LogicalOperator.AND, left, right);
    }

    public static SymbolLogicalOperator OR(Location left, Location right) {
        return new SymbolLogicalOperator(LogicalOperator.OR, left, right);
    }

    public static SymbolLogicalOperator NOT(Location left, Location right) {
        return new SymbolLogicalOperator(LogicalOperator.NOT, left, right);
    }

    public static SymbolLogicalOperator XOR(Location left, Location right) {
        return new SymbolLogicalOperator(LogicalOperator.XOR, left, right);
    }

    @Override
    public boolean isUnaryCompatible() {
        return this.getOperator() == LogicalOperator.NOT;
    }

    @Override
    protected String getSymbol() {
        return getOperator().getSymbol();
    }

    public enum LogicalOperator {
        AND("&&"), OR("||"), NOT("!"), XOR("^");

        private final String symbol;

        LogicalOperator(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}
