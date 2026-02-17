package com.vgs.compilador.semantic.entries;

import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.type.SymbolType;

/**
 *
 * @author sergi
 */
public class SymbolArrayEntry extends SymbolEntry {

    private final int[] dimensions;           // Tamaño de cada dimensión
    private final SymbolType elementType;      // Tipo de los elementos
    private final int dimensionCount;          // Número de dimensiones

    public SymbolArrayEntry(String name, SymbolType arrayType, int[] dimensions, SymbolBase declaration) {
        super(name, arrayType, EntryKind.ARRAY, declaration);
        this.dimensions = dimensions.clone();
        this.dimensionCount = dimensions.length;
        this.elementType = inferElementType(arrayType);
    }

    /**
     * Infiere el tipo de los elementos del array. Ejemplo: int[][] ->
     * elementType = int
     */
    private SymbolType inferElementType(SymbolType arrayType) {
        // Esto depende de cómo representes los tipos de array
        // Podrías tener SymbolType.INT_ARRAY, etc.
        return arrayType; // Simplificado
    }

    public int[] getDimensions() {
        return dimensions.clone();
    }

    public int getDimensionCount() {
        return dimensionCount;
    }

    public SymbolType getElementType() {
        return elementType;
    }

    public int getTotalSize() {
        int size = 1;
        for (int dim : dimensions) {
            size *= dim;
        }
        return size;
    }

    public boolean hasFixedSize() {
        for (int dim : dimensions) {
            if (dim <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Array: ").append(getId()).append(" : ");
        for (int i = 0; i < dimensionCount; i++) {
            sb.append("[").append(dimensions[i]).append("]");
        }
        sb.append(" of ").append(elementType);
        return sb.toString();
    }
}
