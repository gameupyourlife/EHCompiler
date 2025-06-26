package ast;

import java.util.ArrayList;
import java.util.List;

import ast.types.Type;

public class Method {
    public Type type;
    public String name;
    public List<Parameter> parameters;
    public List<Statement> statement;

    public Boolean staticFlag;
    public Method(Type type, String name, List<Parameter> parameters, List<Statement> statement, Boolean staticFlag) {
        this.type = type;
        this.name = name;
        this.parameters = parameters;
        this.statement = statement;
        this.staticFlag = staticFlag;
    }
    public Method(){
        this.parameters = new ArrayList<>();
        this.statement = new ArrayList<>();
    }
}
