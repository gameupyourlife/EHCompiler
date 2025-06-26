package scannerparserlexer.adapter;

import ast.Constructor;
import ast.Parameter;
import ast.Statement;
import scannerparserlexer.parser.ASTParser;
import java.util.ArrayList;
import java.util.List;

public class ConstructorAdapter {
    public static Constructor adapt(ASTParser.ConstructorDeclarationContext ctx) {
        String constructorName = ctx.Identifier().getText();
        
        List<Parameter> parameters = new ArrayList<>();
        if (ctx.formalParameters() != null) {
            for (ASTParser.FormalParameterContext paramCtx : ctx.formalParameters().formalParameter()) {
                parameters.add(ParameterAdapter.adapt(paramCtx));
            }
        }
        
        List<Statement> statements = new ArrayList<>();
        if (ctx.block() != null && ctx.block().statement() != null) {
            for (ASTParser.StatementContext stmtCtx : ctx.block().statement()) {
                statements.add(StatementAdapter.adapt(stmtCtx));
            }
        }
        
        return new Constructor(constructorName, parameters, statements);
    }
}