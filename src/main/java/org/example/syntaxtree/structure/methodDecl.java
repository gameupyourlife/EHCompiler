package org.example.syntaxtree.structure;

import org.example.visitor.semanticVisitor;
import org.example.semantic.typeCheckResult;

public class methodDecl {
    private final String identifier;

    public methodDecl(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public typeCheckResult accept(semanticVisitor visitor) {
        return visitor.typeCheck(this); // Methode muss im Visitor noch ergänzt werden
    }
}
