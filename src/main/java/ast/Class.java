package ast;

import java.util.List;

public class Class {
    public String name;
    public List<Field> fields;
    public List<Method> methods;
    public List<Constructor> constructors;
    public String parentClass;

    public Class(String name) {
        this.name = name;
    }

    public Class (String name, List<Field> fields, List<Method> methods, List<Constructor> constructors, String parentClass) {
        this.name = name;
        this.fields = fields;
        this.methods = methods;
        this.constructors = constructors;
        this.parentClass = parentClass;
    }
}
