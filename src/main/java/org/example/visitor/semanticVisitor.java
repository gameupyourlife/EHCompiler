package org.example.visitor;

import ast.Class;
import ast.Field;
import ast.Method;
import ast.Program;
import ast.Statement;
import ast.Type;
import org.example.semantic.typeCheckResult;

public interface semanticVisitor {
    typeCheckResult typeCheck(Program program);
    typeCheckResult typeCheck(Class classDecl);
    typeCheckResult typeCheck(Field varDecl);
    typeCheckResult typeCheck(Method methodDecl);
    typeCheckResult typeCheck(Statement stmt, Type expectedReturnType);
}