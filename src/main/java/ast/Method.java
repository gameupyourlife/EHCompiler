package ast;

import java.util.List;

import ast.types.Type;

public class Method {
    public Type type;
    public String name;
    public List<Parameter> parameters;
    public List<Statement> statement;
    // Block
    public Boolean staticFlag;
    public Method(Type type, String name, List<Parameter> parameters, List<Statement> statement, Boolean staticFlag) {
        this.type = type;
        this.name = name;
        this.parameters = parameters;
        this.statement = statement;
        this.staticFlag = staticFlag;
    }
    public Method(){
        
    }
}
