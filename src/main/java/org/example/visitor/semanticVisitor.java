package org.example.visitor;

import org.example.semantic.typeCheckResult;
import org.example.syntaxtree.structure.classDecl;
import org.example.syntaxtree.structure.methodDecl;
import org.example.syntaxtree.structure.program;
import org.example.syntaxtree.structure.varDecl;

public interface semanticVisitor {
    typeCheckResult typeCheck(program program);
    typeCheckResult typeCheck(classDecl classDecl);
    typeCheckResult typeCheck(varDecl varDecl);
    typeCheckResult typeCheck(methodDecl methodDecl);
}