
package bytecode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class VarContext {
    private final Map<String, Integer> localVarTable = new HashMap<>();
    private int nextLocalIndex = 0;
    private final Stack<Map<String, Integer>> scopes = new Stack<>();

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void leaveScope() {
        if (!scopes.isEmpty()) {
            Map<String, Integer> currentScope = scopes.pop();
            // Entferne Variablen aus diesem Scope aus der globalLen Tabelle
            for (String varName : currentScope.keySet()) {
                localVarTable.remove(varName);
            }
        }
    }

    public int getLocalIndex(String varName) {
        Integer index = localVarTable.get(varName);
        if (index == null) {
            System.out.println("Variable nicht gefunden: " + varName);
            System.out.println("Verfügbare Variablen: " + localVarTable.keySet());
            throw new RuntimeException("Variable not declared: " + varName);
        }
        System.out.println("Variable " + varName + " hat Index: " + index);
        return index;
    }

    public void declareVariable(String varName) {
        if (localVarTable.containsKey(varName)) {
            throw new RuntimeException("Variable already declared: " + varName);
        }
        int index = nextLocalIndex++;
        localVarTable.put(varName, index);
        if (!scopes.isEmpty()) {
            scopes.peek().put(varName, index);
        }
    }

    public Map<String, Integer> getLocalVarTable() {
        return new HashMap<>(localVarTable);
    }

    // Neue Methode für maximale Anzahl lokaler Variablen
    public int getMaxLocals() {
        return nextLocalIndex;
    }
}