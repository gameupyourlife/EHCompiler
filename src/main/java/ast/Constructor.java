package ast;

import java.util.List;

public class Constructor {
    public String name;
    public List<Parameter> parameters;
    public List<Statement> statements;

    public Constructor(String name) {
        this.name = name;
    }

    public Constructor(String name, List<Parameter> parameters, List<Statement> statements) {
        this.name = name;
        this.parameters = parameters;
        this.statements = statements;
    }
}