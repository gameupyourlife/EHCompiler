package scannerparserlexer.adapter;

import ast.Expression;
import ast.exprStatements.Assign;
import ast.exprStatements.MethodCall;
import scannerparserlexer.parser.ASTParser;

public class ExpressionAdapter {
    public static Expression adapt(ASTParser.ExpressionContext ctx) {
        if (ctx instanceof ASTParser.PrimaryExprContext) {
            ASTParser.PrimaryExprContext primaryCtx = (ASTParser.PrimaryExprContext) ctx;
            return adaptPrimary(primaryCtx.primary());
        } else if (ctx instanceof ASTParser.MethodCallExprContext) {
            ASTParser.MethodCallExprContext methodCallCtx = (ASTParser.MethodCallExprContext) ctx;
            return adaptMethodCall(methodCallCtx);
        } else if (ctx instanceof ASTParser.AssignExprContext) {
            ASTParser.AssignExprContext assignCtx = (ASTParser.AssignExprContext) ctx;
            return adaptAssign(assignCtx);
        } else if (ctx instanceof ASTParser.NewExprContext) {
            ASTParser.NewExprContext newCtx = (ASTParser.NewExprContext) ctx;
            return adaptNewExpr(newCtx);
        } else {
            // Default case for other expression types
            return new ast.expressions.EmptyExpression();
        }
    }
    
    private static Expression adaptPrimary(ASTParser.PrimaryContext ctx) {
        if (ctx.Identifier() != null) {
            return new ast.expressions.Identifier(ctx.Identifier().getText());
        } else if (ctx.literal() != null) {
            return adaptLiteral(ctx.literal());
        } else {
            return new ast.expressions.EmptyExpression();
        }
    }
    
    private static Expression adaptLiteral(ASTParser.LiteralContext ctx) {
        if (ctx.IntegerLiteral() != null) {
            return new ast.expressions.IntConst(Integer.parseInt(ctx.IntegerLiteral().getText()));
        } else if (ctx.BooleanLiteral() != null) {
            return new ast.expressions.BooleanLiteral(Boolean.parseBoolean(ctx.BooleanLiteral().getText()));
        } else if (ctx.CharacterLiteral() != null) {
            String charText = ctx.CharacterLiteral().getText();
            // Remove the quotes
            char charValue = charText.substring(1, charText.length() - 1).charAt(0);
            return new ast.expressions.CharConst(charValue);
        } else if (ctx.StringLiteral() != null) {
            String stringText = ctx.StringLiteral().getText();
            // Remove the quotes
            String stringValue = stringText.substring(1, stringText.length() - 1);
            return new ast.expressions.StringConst(stringValue);
        } else {
            return new ast.expressions.Null();
        }
    }
    
    private static Expression adaptMethodCall(ASTParser.MethodCallExprContext ctx) {
        MethodCall methodCall = new MethodCall();
        if (ctx.expression() != null) {
            methodCall.target = adapt(ctx.expression());
        }
        if (ctx.Identifier() != null) {
            methodCall.methodName = ctx.Identifier().getText();
        }
        if (ctx.expressionList() != null) {
            methodCall.arguments = new java.util.ArrayList<>();
            for (ASTParser.ExpressionContext exprCtx : ctx.expressionList().expression()) {
                methodCall.arguments.add(adapt(exprCtx));
            }
        }
        return methodCall;
    }

    private static Expression adaptAssign(ASTParser.AssignExprContext ctx) {
        Assign assign = new Assign();
        if (ctx.expression() != null && ctx.expression().size() >= 2) {
            assign.target = adapt(ctx.expression(0));
            assign.value = adapt(ctx.expression(1));
        }
        return assign;
    }

    private static Expression adaptNewExpr(ASTParser.NewExprContext ctx) {
        MethodCall constructor = new MethodCall();
        if (ctx.Identifier() != null) {
            constructor.methodName = ctx.Identifier().getText();
        }
        if (ctx.expressionList() != null) {
            constructor.arguments = new java.util.ArrayList<>();
            for (ASTParser.ExpressionContext exprCtx : ctx.expressionList().expression()) {
                constructor.arguments.add(adapt(exprCtx));
            }
        }
        return constructor;
    }
}