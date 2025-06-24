package ast;

import ast.types.Type;

public class Parameter {
    public Type type;
    public String name;
    public Parameter(Type type, String name) {
        this.type = type;
        this.name = name;
    }
    public Parameter() {
        
    }
}
