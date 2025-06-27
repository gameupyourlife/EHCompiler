package scannerparserlexer.adapter;
import scannerparserlexer.parser.ASTLexer;
import ast.Class;
import ast.Constructor;
import ast.Field;
import ast.Method;
import scannerparserlexer.parser.ASTParser;


import java.util.ArrayList;
import java.util.List;

public class ClassAdapter {
    public static Class adapt(ASTParser.ClassDeclarationContext ctx) {
        String className = ctx.Identifier(0).getText();
        
        List<Field> fields = new ArrayList<>();
        List<Method> methods = new ArrayList<>();
        List<Constructor> constructors = new ArrayList<>();
        
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
            
            if (ctx.classBody().constructorDeclaration() != null) {
                for (ASTParser.ConstructorDeclarationContext constructorCtx : ctx.classBody().constructorDeclaration()) {
                    constructors.add(ConstructorAdapter.adapt(constructorCtx));
                }
            }
        }
        
        // Add default constructor if no constructors are present
        if (constructors.isEmpty()) {
            constructors.add(new Constructor(className, new ArrayList<>(), new ArrayList<>()));
        }
        
        // Handle inheritance - explicit or implicit Object
        String parentClass = "Object";
        if (ctx.Identifier().size() > 1) {
            // Second identifier is the parent class
            parentClass = ctx.Identifier(1).getText();
        }
        
        return new Class(className, fields, methods, constructors, parentClass);
    }
}