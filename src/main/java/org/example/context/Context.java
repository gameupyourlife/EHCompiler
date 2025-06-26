package org.example.context;

import ast.Method;
import ast.Program;
import ast.types.Type;
import ast.Class;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Context {
    private final Program program;

    // lokale Scopes für Variablen und Parameter
    private final Deque<Map<String, Type>> variableScopes = new ArrayDeque<>();

    // Felder je Klasse
    private final Map<String, Map<String, Type>> classFields = new HashMap<>();

    // Stack, um aktuellen Klassennamen zu wissen
    private final Deque<String> classStack = new ArrayDeque<>();

    public Context(Program program) {
        this.program = program;
        // globaler Scope initialisieren
        this.variableScopes.push(new HashMap<>());
    }

    // === Klassen-Kontext ===
    public void enterClass(String className) {
        classStack.push(className);
    }

    public void exitClass() {
        classStack.pop();
    }

    private String getCurrentClassName() {
        return classStack.peek();
    }

    // === Scopes verwalten ===
    public void enterScope() {
        variableScopes.push(new HashMap<>());
    }

    public void exitScope() {
        variableScopes.pop();
    }

    public boolean declareVariable(String name, Type type) {
        Map<String, Type> current = variableScopes.peek();
        if (current.containsKey(name)) {
            return false;
        }
        current.put(name, type);
        return true;
    }

    // === lookupVariable: lokale Variable oder Feld ===
    public Type lookupVariable(String name) {
        // 1) lokale Scopes
        for (Map<String, Type> scope : variableScopes) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        // 2) Feld der aktuellen Klasse
        return lookupField(name);
    }

    // === Felder auflösen ===
    public Type lookupField(String name) {
        String cls = getCurrentClassName();
        if (cls == null) return null;
        Map<String, Type> fields = classFields.get(cls);
        if (fields != null && fields.containsKey(name)) {
            return fields.get(name);
        }
        return null;
    }

    public void registerField(String className, String fieldName, Type type) {
        classFields
                .computeIfAbsent(className, k -> new HashMap<>())
                .put(fieldName, type);
    }

    // === Hilfs­methoden ===
    public boolean typeExists(Type type) {
        return type != null;
    }

    public Program getProgram() {
        return program;
    }

    public Method findMethod(Type type, String methodName) {
        if (type == null || methodName == null) return null;
        Class cls = findClassByType(type);
        if (cls == null || cls.methods == null) return null;
        for (Method m : cls.methods) {
            if (m.name.equals(methodName)) {
                return m;
            }
        }
        return null;
    }

    private Class findClassByType(Type type) {
        if (type == null) return null;
        String typeName = type.name();  // z.B. "INT", "MyClass"
        for (Class c : program.classes) {
            if (c.name.equalsIgnoreCase(typeName)) {
                return c;
            }
        }
        return null;
    }
}
