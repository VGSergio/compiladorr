package com.vgs.compilador.symbols.helpers;

import com.vgs.compilador.symbols.type.SymbolType;
import com.vgs.compilador.symbols.value.SymbolLiteral;
import com.vgs.compilador.symbols.value.SymbolValue;
import java.util.ArrayList;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public final class SymbolArrayValues extends SymbolValue {

    private final ArrayList<SymbolValue> values;
    private int dimensions;

    public SymbolArrayValues(SymbolValue value, Location left, Location right) {
        super("ArrayContent", left, right);
        this.values = new ArrayList<>();
        this.dimensions = 1;
        addValue(value);
    }

    public void addValue(SymbolValue value) {
        if (value instanceof SymbolArrayValues v && values.isEmpty()) {
            dimensions += v.getDimensions();
        }
        values.add(value);
    }

    public SymbolValue[] getValues() {
        return values.toArray(SymbolValue[]::new);
    }

    public int getDimensions() {
        return dimensions;
    }

    public int getLength() {
        return values.size();
    }

    public SymbolLiteral[] getLengths() {
        return getLengthsList()
                .stream()
                .map(i -> new SymbolLiteral(SymbolType.INTEGER(getLeft(), getRight()), i))
                .toArray(SymbolLiteral[]::new);
    }

    private ArrayList<Integer> getLengthsList() {
        ArrayList<Integer> lengths = new ArrayList<>();
        lengths.add(values.size());
        if (values.getFirst() instanceof SymbolArrayValues v) {
            lengths.addAll(v.getLengthsList());
        }
        return lengths;
    }

    public SymbolValue[] getValuesFlatten() {
        return getValuesList().toArray(SymbolValue[]::new);
    }

    private ArrayList<SymbolValue> getValuesList() {
        ArrayList<SymbolValue> valuesList = new ArrayList<>();
        for (SymbolValue value1 : values) {
            if (value1 instanceof SymbolArrayValues v) {
                valuesList.addAll(v.getValuesList());
            } else {
                valuesList.add(value1);
            }
        }
        return valuesList;
    }

    @Override
    public String toString() {
        int size = values.size();
        if (size == 0) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{').append(values.getFirst());
        for (int i = 1; i < size; i++) {
            sb.append(", ").append(values.get(i));
        }

        return sb.append("}").toString();
    }
}
