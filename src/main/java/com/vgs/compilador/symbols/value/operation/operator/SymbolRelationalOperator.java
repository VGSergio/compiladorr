package com.vgs.compilador.symbols.value.operation.operator;

import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolRelationalOperator extends SymbolOperator<SymbolRelationalOperator.RelationalOperator> {

    private SymbolRelationalOperator(RelationalOperator type, Location left, Location right) {
        super("RelationalOperator", OperationType.RELATIONAL, type, left, right);
    }

    public static SymbolRelationalOperator EQ(Location left, Location right) {
        return new SymbolRelationalOperator(RelationalOperator.EQ, left, right);
    }

    public static SymbolRelationalOperator NE(Location left, Location right) {
        return new SymbolRelationalOperator(RelationalOperator.NE, left, right);
    }

    public static SymbolRelationalOperator GT(Location left, Location right) {
        return new SymbolRelationalOperator(RelationalOperator.GT, left, right);
    }

    public static SymbolRelationalOperator LT(Location left, Location right) {
        return new SymbolRelationalOperator(RelationalOperator.LT, left, right);
    }

    public static SymbolRelationalOperator GE(Location left, Location right) {
        return new SymbolRelationalOperator(RelationalOperator.GE, left, right);
    }

    public static SymbolRelationalOperator LE(Location left, Location right) {
        return new SymbolRelationalOperator(RelationalOperator.LE, left, right);
    }

    @Override
    public boolean isUnaryCompatible() {
        return false;
    }

    @Override
    protected String getSymbol() {
        return getOperator().getSymbol();
    }

    public enum RelationalOperator {
        EQ("=="), NE("!="), GT(">"), LT("<"), GE(">="), LE("<=");

        private final String symbol;

        RelationalOperator(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}
