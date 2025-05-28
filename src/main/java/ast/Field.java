package ast;

public class Field {
    public String name;
    public Type type;
    public String defaultValue;

    public Field (String name, Type type, String defaultValue)
    {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }
    public Field(){}
}
