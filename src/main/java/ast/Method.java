package ast;

import java.util.List;

public class Method {
    public Type type;
    public String name;
    public List<Parameter> parameters;
    public Statement statement;
    public Boolean staticFlag;
}
