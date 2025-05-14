package org.example.syntaxtree.structure;

import org.example.visitor.semanticVisitor;
import org.example.context.context;
import java.util.List;

public class program {
    private List<classDecl> classes;
    private context context;

    public program(List<classDecl> classes) {
        this.classes = classes;
    }

    public List<classDecl> getClasses() {
        return classes;
    }

    public void setContext(context context) {
        this.context = context;
    }

    public context getContext() {
        return context;
    }

    public <T> T accept(semanticVisitor visitor) {
        return (T) visitor.typeCheck(this);
    }
}