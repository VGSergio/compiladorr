package com.vgs.compilador.symbols.value.access;

import com.vgs.compilador.manager.ErrorManager;
import com.vgs.compilador.symbols.type.SymbolType;
import com.vgs.compilador.symbols.value.SymbolValue;
import java.util.ArrayList;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 *
 * @author sergi
 */
public class SymbolFunctionCall extends SymbolAccess {

    public SymbolFunctionCall(String name, String identifier, Location left, Location right) {
        super(name, identifier, left, right);
    }

//    private final ArrayList<SymbolValue<?>> arguments;  // Argumentos de la llamada
//
//    public SymbolFunctionCall(String identifier, Location left, Location right) {
//        this(identifier, new ArrayList<>(), left, right);
//    }
//
//    public SymbolFunctionCall(String identifier, ArrayList<SymbolValue<?>> arguments,
//            Location left, Location right) {
//        super("FunctionCall", identifier, left, right);
//        this.arguments = arguments != null ? arguments : new ArrayList<>();
//    }
//
//    public ArrayList<SymbolValue<?>> getArguments() {
//        return arguments;
//    }
//
//    public int getArgumentCount() {
//        return arguments.size();
//    }
//
//    public SymbolValue<?> getArgument(int index) {
//        return arguments.get(index);
//    }
//
//    /**
//     * Valida que los argumentos coincidan con los parámetros de la función. Se
//     * llama durante el análisis semántico después de setFunction().
//     */
//    public boolean validateArguments() {
//        if (function == null) {
//            return false;
//        }
//
//        ArrayList<SymbolParameter> params = function.getParameters();
//        if (params.size() != arguments.size()) {
//            ErrorManager.semantic(this,
//                    String.format("Function %s expects %d arguments, got %d",
//                            identifier, params.size(), arguments.size()));
//            return false;
//        }
//
//        for (int i = 0; i < params.size(); i++) {
//            SymbolType paramType = params.get(i).getType();
//            SymbolType argType = arguments.get(i).getType();
//
//            if (!paramType.equals(argType)) {
//                ErrorManager.semantic(this,
//                        String.format("Argument %d of function %s: expected %s, got %s",
//                                i + 1, identifier, paramType, argType));
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder(identifier + "(");
//        for (int i = 0; i < arguments.size(); i++) {
//            if (i > 0) {
//                sb.append(", ");
//            }
//            sb.append(arguments.get(i));
//        }
//        sb.append(")");
//        return sb.toString();
//    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
