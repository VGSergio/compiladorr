package com.vgs.compilador.semantic.entries;

import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.value.SymbolType;

/**
 *
 * @author sergi
 */
public abstract class SymbolEntry {

    private final String id;                    // Nombre del identificador
    private final SymbolType type;              // Tipo asociado
    private final EntryKind kind;               // Qué tipo de entrada es
    private final SymbolBase declaration;       // Nodo AST donde se declaró

    protected SymbolEntry(String id, SymbolType type, EntryKind kind, SymbolBase declaration) {
        this.id = id;
        this.type = type;
        this.kind = kind;
        this.declaration = declaration;
    }

    public String getId() {
        return id;
    }

    public SymbolType getType() {
        return type;
    }

    public SymbolBase getDeclaration() {
        return declaration;
    }

    public EntryKind getKind() {
        return kind;
    }

    public boolean isVariable() {
        return kind == EntryKind.VARIABLE;
    }

    public boolean isArray() {
        return kind == EntryKind.ARRAY;
    }

    public boolean isFunction() {
        return kind == EntryKind.FUNCTION;
    }

    @Override
    public String toString() {
        return kind + ": " + id + " : " + type;
    }

    public enum EntryKind {
        VARIABLE, ARRAY, FUNCTION
    }
}
