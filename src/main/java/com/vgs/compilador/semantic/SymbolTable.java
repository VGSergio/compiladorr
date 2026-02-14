package com.vgs.compilador.semantic;

import com.vgs.compilador.semantic.entries.SymbolEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author sergi
 */
public final class SymbolTable {

    private int n;                  // Ámbito actual
    private final Stack<Scope> ta;  // Tabla de ámbitos
    private final ArrayList<SymbolEntry> td;

    // Contador de los distintos tipos de ámbitos para darles nombre
    private final HashMap<String, Integer> scopeCounters = new HashMap<>();

    public SymbolTable() {
        this.ta = new Stack<>();
        this.n = 0;
        this.td = new ArrayList<>();
        enterBlock(Scope.ScopeType.GLOBAL);
    }

    public void enterBlock(Scope.ScopeType type) {
        Scope newScope = new Scope(ta.size(), getNewBlockName(type));
        ta.add(newScope);
        updateN();
    }

    public void exitBlock() {
        if (n == 0) {
            System.err.println("[exitBlock] Cannot exit GLOBAL scope");
            return;
        }
        deleteScopeVariablesFromTd();
        ta.remove(n);
        updateN();
    }

    private void updateN() {
        n = ta.size() - 1;
    }

    private void deleteScopeVariablesFromTd() {
        // Obetenemos todos los indices de las variables a eliminar
        List<Integer> reverseIndexes = new ArrayList<>(ta.peek().getExpansionTable().values());

        // Hay que eliminar los indices de mayor a menor ya que 
        // la lista decrece segun eliminamos los elementos
        reverseIndexes.sort(Collections.reverseOrder());
        for (int idx : reverseIndexes) {
            td.remove(idx);
        }
    }

    public void put(SymbolEntry entry) {
        String id = entry.getId();
        if (ta.peek().exists(id)) {
            return;
        }
        td.add(entry);
        ta.peek().addEntry(id, td.indexOf(entry));
    }

    private int getDescriptionIdx(String id) {
        int aux = n;
        while (aux >= 0) {
            Scope scope = ta.get(aux);
            Integer idx = scope.getEntryIdx(id);
            if (idx != null) {
                return idx;
            }
            aux--;
        }
        return -1;
    }

    public SymbolEntry getDescription(int idx) {
        if (idx >= 0 && idx < td.size()) {
            return td.get(idx);
        }
        return null;
    }

    public SymbolEntry getDescription(String id) {
        return getDescription(getDescriptionIdx(id));
    }

    /**
     * Método para darle nombre a los ámbitos, útil para DEBUG
     *
     * @param type
     * @return
     */
    private String getNewBlockName(Scope.ScopeType type) {
        String base = type.name();
        int count = scopeCounters.getOrDefault(base, 0);
        scopeCounters.put(base, count + 1);
        return (count == 0) ? base : base + "_" + count;
    }

    /**
     * Imprime la lista de ámbitos disponibles, útil para DEBUG
     */
    public void printScopeTable() {
        System.out.println("--- Lista de ámbitos ---");
        for (Scope s : ta) {
            System.out.println(s);
        }
    }

    public void printScope(Scope scope) {
        System.out.println(scope);
        System.out.println(scope.getExpansionTable());
    }

    public static class Scope {

        private final int n;        // Nivel de ámbito
        private final String name;  // Nombre del ámbito
        private final HashMap<String, Integer> te;   // Tabla de expansión del ámbito

        /**
         * Un ámbito esta formado por su indicador de nivel, su nombre y su
         * tabla de expansión.
         *
         * @param n
         * @param name
         */
        public Scope(int n, String name) {
            this.n = n;
            this.name = name;
            this.te = new HashMap<>();
        }

        /**
         * Devuelve el nivel del ámbito.
         *
         * @return
         */
        public int getN() {
            return n;
        }

        /**
         * Devuelve el nombre del ámbito.
         *
         * @return
         */
        public String getName() {
            return name;
        }

        public HashMap<String, Integer> getExpansionTable() {
            return te;
        }

        public boolean exists(String id) {
            return te.containsKey(id);
        }

        public void addEntry(String id, int idx) {
            te.put(id, idx);
        }

        public Integer getEntryIdx(String id) {
            return te.get(id);
        }

        @Override
        public String toString() {
            return String.format("[%d] %s", n, name);
        }

        /**
         * Enum con los distintos nombres posibles para los ámbitos. Util para
         * DEBUG.
         */
        public enum ScopeType {
            GLOBAL, TEST
        }
    }
}
