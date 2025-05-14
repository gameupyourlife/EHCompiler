package scannerparserlexer.adapter;

import ast.Class;
import ast.Field;
import ast.Method;
import scannerparserlexer.parser.ASTParser;

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter {
    public static Class adapt(ASTParser.ClassDeclarationContext ctx) {
        String className = ctx.Identifier().getText();
        
        List<Field> fields = new ArrayList<>();
        List<Method> methods = new ArrayList<>();
        
        if (ctx.classBody() != null) {
            if (ctx.classBody().fieldDeclaration() != null) {
                for (ASTParser.FieldDeclarationContext fieldCtx : ctx.classBody().fieldDeclaration()) {
                    fields.add(FieldAdapter.adapt(fieldCtx));
                }
            }
            
            if (ctx.classBody().methodDeclaration() != null) {
                for (ASTParser.MethodDeclarationContext methodCtx : ctx.classBody().methodDeclaration()) {
                    methods.add(MethodAdapter.adapt(methodCtx));
                }
            }
        }
        
        return new Class(className, fields, methods, null);
    }
}