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
    private Program program;
    private final Deque<Map<String, Type>> scopes = new ArrayDeque<>();

    private final Deque<String> classStack = new ArrayDeque<>();
    private final Map<String, Map<String, Type>> classFields = new HashMap<>();

    public Context(Program program) {
        this.program = program;
        // globaler Scope
        this.scopes.push(new HashMap<>());
    }

    public Type lookupField(String name) {
        String cls = getCurrentClassName();
        if (cls == null) {
            return null;
        }
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

    private String getCurrentClassName() {
        return classStack.peek();
    }

    public Program getProgram() {
        return program;
    }

    public boolean typeExists(Type type) {
        return type != null;
    }

    public void enterClass(String className) {
        classStack.push(className);
    }

    public void exitClass() {
        classStack.pop();
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        scopes.pop();
    }

    public boolean declareVariable(String name, Type type) {
        Map<String, Type> current = scopes.peek();
        if (current.containsKey(name)) {
            return false;
        }
        current.put(name, type);
        return true;
    }

    public Method findMethod(Type type, String methodName) {
        ast.Class cls = findClassByType(type);
        if (cls == null || cls.methods == null) {
            return null;
        }
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
}