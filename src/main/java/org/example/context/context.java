package org.example.context;

import ast.Method;
import ast.Program;
import ast.Type;
import ast.Class;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class context {
    private Program program;
    private final Deque<Map<String, Type>> scopes = new ArrayDeque<>();

    public context(Program program) {
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

    public boolean typeExists(Type type) {
        return type != null;
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        scopes.pop();
    }

    public boolean declareVariable(String name, Type type) {
        Map<String, Type> current = scopes.peek();
        assert current != null;
        if (current.containsKey(name)) {
            return false;
        }
        current.put(name, type);
        return true;
    }

    public Method findMethod(Type type, String methodName) {
        if (type == null || methodName == null) return null;

        Class cls = findClassByType(type);
        if (cls == null || cls.methods == null) return null;

        for (Method method : cls.methods) {
            if (method.name.equals(methodName)) {
                return method;
            }
        }

        return null;
    }

    private Class findClassByType(Type type) {
        if (type == null) return null;

        String typeName = type.name(); // z.B. INT, BOOLEAN, MyClass

        for (Class cls : program.classes) {
            if (cls.name.equalsIgnoreCase(typeName)) {
                return cls;
            }
        }

        return null;
    }

    public Type lookupVariable(String name) {
        for (Map<String, Type> scope : scopes) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }
}