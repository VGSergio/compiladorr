package com.vgs.compilador.symbols.value.operation;

import com.vgs.compilador.symbols.SymbolBase;
import java.util.HashMap;
import java.util.Map;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolOperator extends SymbolBase {

    private final Operator operator;

    public SymbolOperator(Operator operator, Location left, Location right) {
        super(operator.toString(), left, right);
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public OperatorType getOperatorType() {
        return operator.operationType;
    }

    public boolean isUnaryCompatible() {
        return operator.isUnaryCompatible;
    }

    @Override
    public String toString() {
        return operator.toString();
    }

    public enum Operator {
        // Arithmetic
        ADD("+", OperatorType.ARITHMETIC, false),
        SUB("-", OperatorType.ARITHMETIC, true),
        MUL("*", OperatorType.ARITHMETIC, false),
        DIV("/", OperatorType.ARITHMETIC, false),
        MOD("%", OperatorType.ARITHMETIC, false),
        // Logical
        AND("&&", OperatorType.LOGICAL, false),
        OR("||", OperatorType.LOGICAL, false),
        NOT("!", OperatorType.LOGICAL, true),
        XOR("^", OperatorType.LOGICAL, false),
        // Relational
        EQ("==", OperatorType.RELATIONAL, false),
        NE("!=", OperatorType.RELATIONAL, false),
        GT(">", OperatorType.RELATIONAL, false),
        LT("<", OperatorType.RELATIONAL, false),
        GE(">=", OperatorType.RELATIONAL, false),
        LE("<=", OperatorType.RELATIONAL, false);

        public final String symbol;
        public final OperatorType operationType;
        public final boolean isUnaryCompatible;
        
        private static final Map<String, Operator> SYMBOL_MAP = new HashMap<>();

        static {
            for (Operator op : values()) {
                SYMBOL_MAP.put(op.symbol, op);
            }
        }
        
        Operator(String symbol, OperatorType operatorType, boolean isUnaryCompatible) {
            this.symbol = symbol;
            this.operationType = operatorType;
            this.isUnaryCompatible = isUnaryCompatible;
        }

        public static Operator fromString(String s) {
            return SYMBOL_MAP.get(s);
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public enum OperatorType {
        ARITHMETIC, RELATIONAL, LOGICAL
    }
}
