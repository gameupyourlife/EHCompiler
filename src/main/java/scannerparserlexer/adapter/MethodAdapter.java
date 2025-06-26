package scannerparserlexer.adapter;

import ast.Method;
import parser.ASTParser;

import java.util.ArrayList;

public class MethodAdapter {
    public static Method adapt(ASTParser.MethodDeclarationContext ctx) {
        Method method = new Method();

        // Set method name
        method.name = ctx.Identifier().getText();

        // Set static flag based on grammar (must be set before other checks)
        method.staticFlag = ctx.getChild(0).getText().equals("static");

        // Set method type
        method.type = TypeAdapter.adapt(ctx.type());

        // Set parameters
        method.parameters = new ArrayList<>();
        if (ctx.formalParameters() != null) {
            for (ASTParser.FormalParameterContext paramCtx : ctx.formalParameters().formalParameter()) {
                method.parameters.add(ParameterAdapter.adapt(paramCtx));
            }
        }

        // Special handling for main method - automatically add String[] args parameter
        if (method.name.equals("main") && method.staticFlag &&
                method.type != null && method.type == ast.types.Type.VOID &&
                method.parameters.isEmpty()) {
            // Create String[] args parameter for main method
            ast.Parameter stringArrayParam = new ast.Parameter();
            stringArrayParam.name = "args";
            stringArrayParam.type = ast.types.Type.STRING_ARRAY;
            method.parameters.add(stringArrayParam);
        }

        // Set method body (statement)
        if (ctx.block() != null) {
            // adaptBlock liefert ein ast.statements.Block
            ast.statements.Block b = (ast.statements.Block) StatementAdapter.adaptBlock(ctx.block());
            // und hier holen wir uns die darin enthaltene List<Statement>
            method.statement = b.statements;
        }

        return method;
    }
}