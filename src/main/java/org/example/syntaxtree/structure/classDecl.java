package org.example.syntaxtree.structure;

import org.example.visitor.semanticVisitor;
import java.util.List;

public class classDecl {
    private final String identifier;
    private final List<varDecl> fields;
    private final List<methodDecl> methods;

    public classDecl(String identifier, List<varDecl> fields, List<methodDecl> methods) {
        this.identifier = identifier;
        this.fields = fields;
        this.methods = methods;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<varDecl> getFields() {
        return fields;
    }

    public List<methodDecl> getMethods() {
        return methods;
    }

    public <T> T accept(semanticVisitor visitor) {
        return (T) visitor.typeCheck(this);
    }
}