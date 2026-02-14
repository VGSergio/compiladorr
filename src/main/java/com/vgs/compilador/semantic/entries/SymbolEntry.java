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
    private final SymbolBase declaration;       // Nodo AST donde se declaró
    private final EntryKind kind;               // Qué tipo de entrada es

    protected SymbolEntry(String id, SymbolType type, SymbolBase declaration, EntryKind kind) {
        this.id = id;
        this.type = type;
        this.declaration = declaration;
        this.kind = kind;
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
