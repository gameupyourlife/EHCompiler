package org.example.visitor;

import org.example.semantic.typeCheckResult;
import org.example.syntaxtree.structure.classDecl;
import org.example.syntaxtree.structure.program;

public interface semanticVisitor {
    typeCheckResult typeCheck(program program);
    typeCheckResult typeCheck(classDecl classDecl);
}