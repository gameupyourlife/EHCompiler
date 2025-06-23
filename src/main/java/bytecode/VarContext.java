package bytecode;

import java.util.HashMap;
import java.util.Map;

public class VarContext {

    private final Map<String, Integer> localVarTable = new HashMap<>();
    private int nextLocalIndex = 0;

    public int getOrCreateLocalIndex(String varName) {
        return localVarTable.computeIfAbsent(varName, v -> nextLocalIndex++);
    }

    public int getLocalIndex(String varName) {
        if (!localVarTable.containsKey(varName)) {
            throw new RuntimeException("Variable not declared: " + varName);
        }
        return localVarTable.get(varName);
    }

    public void declareVariable(String varName) {
        if (localVarTable.containsKey(varName)) {
            throw new RuntimeException("Variable already declared: " + varName);
        }
        localVarTable.put(varName, nextLocalIndex++);
    }

    public Map<String, Integer> getLocalVarTable() {
        return localVarTable;
    }
}
