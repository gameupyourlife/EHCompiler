package scannerparserlexer.adapter;

import ast.Method;
import ast.Parameter;
import ast.Statement;
import scannerparserlexer.parser.ASTParser;

import java.util.ArrayList;
import java.util.List;

public class MethodAdapter {
    public static Method adapt(ASTParser.MethodDeclarationContext ctx) {
        Method method = new Method();
        
        // Set method name
        method.name = ctx.Identifier().getText();
        
        // Set method type
        method.type = TypeAdapter.adapt(ctx.type());
        
        // Set parameters
        method.parameters = new ArrayList<>();
        if (ctx.formalParameters() != null) {
            for (ASTParser.FormalParameterContext paramCtx : ctx.formalParameters().formalParameter()) {
                method.parameters.add(ParameterAdapter.adapt(paramCtx));
            }
        }
        
        // Set method body (statement)
        if (ctx.block() != null) {
            method.statement = StatementAdapter.adaptBlock(ctx.block());
        }
        
        // Set static flag (not supported in the grammar yet)
        method.staticFlag = false;
        
        return method;
    }
}