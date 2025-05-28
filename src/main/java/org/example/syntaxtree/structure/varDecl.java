package org.example.syntaxtree.structure;

import  org.example.visitor.semanticVisitor;


public class varDecl {
    private final String identifier;

    public varDecl(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public <T> T accept(semanticVisitor visitor) {
        return (T) visitor.typeCheck(this);
    }
}
