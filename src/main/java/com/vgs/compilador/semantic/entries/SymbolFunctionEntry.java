package com.vgs.compilador.semantic.entries;

import com.vgs.compilador.symbols.SymbolBase;
import com.vgs.compilador.symbols.type.SymbolType;

/**
 *
 * @author sergi
 */
public class SymbolFunctionEntry extends SymbolEntry {

    public SymbolFunctionEntry(String name, SymbolType type, SymbolBase declaration, EntryKind kind) {
        super(name, type, kind, declaration);
    }

//    private final ArrayList<SymbolParameter> parameters;
//    private final SymbolType returnType;
//    private final boolean isNative;
//
//    public SymbolFunctionEntry(String name, SymbolType returnType, ArrayList<SymbolParameter> parameters, SymbolBase declaration) {
//        this(name, returnType, parameters, declaration, false);
//    }
//
//    public SymbolFunctionEntry(String name, SymbolType returnType, ArrayList<SymbolParameter> parameters, SymbolBase declaration, boolean isNative) {
//        super(name, returnType, declaration, EntryKind.FUNCTION);
//        this.returnType = returnType;
//        this.parameters = new ArrayList<>(parameters);
//        this.isNative = isNative;
//    }

//    public SymbolType getReturnType() {
//        return returnType;
//    }

//    public ArrayList<SymbolParameter> getParameters() {
//        return parameters;
//    }

//    public int getParameterCount() {
//        return parameters.size();
//    }

//    public boolean isNative() {
//        return isNative;
//    }

//    public boolean isVoid() {
//        return returnType.isVoid();
//    }

//    public SymbolParameter getParameter(int index) {
//        return parameters.get(index);
//    }

//    /**
//     * Comprueba si los tipos de argumentos coinciden con los parámetros.
//     * @param argumentTypes
//     * @return 
//     */
//    public boolean matchesArguments(ArrayList<SymbolType> argumentTypes) {
//        if (argumentTypes.size() != parameters.size()) {
//            return false;
//        }
//
//        for (int i = 0; i < parameters.size(); i++) {
//            if (!parameters.get(i).getType().equals(argumentTypes.get(i))) {
//                return false;
//            }
//        }
//
//        return true;
//    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Function: ").append(getId()).append("(");
//        for (int i = 0; i < parameters.size(); i++) {
//            if (i > 0) {
//                sb.append(", ");
//            }
//            sb.append(parameters.get(i).getType());
//        }
//        sb.append(") -> ").append(returnType);
//        if (isNative) {
//            sb.append(" [native]");
//        }
//        return sb.toString();
//    }
}
